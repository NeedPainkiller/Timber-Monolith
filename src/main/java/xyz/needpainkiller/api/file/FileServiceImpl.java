package xyz.needpainkiller.api.file;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.api.file.dao.FileRepo;
import xyz.needpainkiller.api.file.model.FileEntity;
import xyz.needpainkiller.base.file.FileService;
import xyz.needpainkiller.base.file.error.FileException;
import xyz.needpainkiller.base.file.model.FileAuthorityType;
import xyz.needpainkiller.base.file.model.FileServiceType;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.helper.TimeHelper;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static xyz.needpainkiller.base.file.error.FileErrorCode.*;


@Slf4j
@Service
public class FileServiceImpl implements FileService<FileEntity> {

    private static final Predicate<FileEntity> notUsedFile = fileEntity -> {
        String fileServiceName = fileEntity.getFileService();
        Long fileServiceId = fileEntity.getFileServiceId();
        FileServiceType fileServiceType = fileEntity.getFileServiceType();
        return fileEntity.isFileExists() && (!fileEntity.isUseYn() || fileServiceName.equals(DEF_FILE_SERVICE_NAME) || fileServiceId.equals(DEF_FILE_SERVICE_ID) || fileServiceType.equals(FileServiceType.DEFAULT) || fileServiceType.equals(FileServiceType.NONE));
    };
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private FileRepo fileRepo;

    @Override
    @Transactional
    public FileEntity selectFile(String uuid) {
        return fileRepo.findByUuid(uuid).filter(availableFile).orElseThrow(() -> new FileException(FILE_DOWNLOAD_FILE_INFO_NOT_EXIST));
    }

    @Override
    @Transactional
    public List<FileEntity> selectFileList(List<String> uuidList) {
        try (Stream<FileEntity> fileEntityStream = fileRepo.streamByUuidIn(uuidList)) {
            return fileEntityStream.filter(availableFile).toList();
        }
    }

    @Override
    @Transactional
    public List<FileEntity> selectFileNotUsed() {
        return fileRepo.findAllBy().stream().filter(notUsedFile).toList();
    }

    @Override
    @Transactional
    public List<FileEntity> selectServiceFileList(String fileServiceName) {
        return fileRepo.findByFileService(fileServiceName).stream().filter(availableFile).toList();
    }

    @Override
    @Transactional
    public List<FileEntity> selectServiceFileList(String fileServiceName, Long fileServiceId) {
        return fileRepo.findByFileServiceAndFileServiceId(fileServiceName, fileServiceId).stream().filter(availableFile).toList();
    }

    @Override
    @Transactional
    public List<FileEntity> selectServiceFileList(String fileServiceName, Long fileServiceId, FileServiceType fileServiceType) {
        return fileRepo.findByFileServiceAndFileServiceIdAndFileServiceType(fileServiceName, fileServiceId, fileServiceType).stream().filter(availableFile).toList();
    }

    @Override
    public void validateFile(FileEntity fileEntity) {
        if (Boolean.FALSE.equals(fileEntity.isUseYn())) {
            log.error("FILE_DOWNLOAD_CAN_NOT_USABLE");
            throw new FileException(FILE_DOWNLOAD_CAN_NOT_USABLE);
        }

        FileAuthorityType fileAuthorityType = fileEntity.getAccessAuthority();
        if (!FileAuthorityType.isExist(fileAuthorityType)) {
            log.error("FILE_DOWNLOAD_FILE_EMPTY_AUTH");
            throw new FileException(FILE_DOWNLOAD_FILE_EMPTY_AUTH);
        }
    }

    @Override
    public void validateAuthority(FileEntity fileEntity, User requester) {
        FileAuthorityType fileAuthorityType = fileEntity.getAccessAuthority();
        if (FileAuthorityType.PRIVATE.equals(fileAuthorityType) || FileAuthorityType.LOGON.equals(fileAuthorityType)) {
            if (FileAuthorityType.PRIVATE.equals(fileAuthorityType) && !fileEntity.getCreatedBy().equals(requester.getId())) {
                log.error("FILE_DOWNLOAD_FILE_NOT_YOU_OWN");
                throw new FileException(FILE_DOWNLOAD_FILE_NOT_YOU_OWN);
            }
        }
    }


    @Override
    @Transactional
    public FileEntity createFileWithUpload(FileEntity fileEntity, User user) throws FileException {
        validateFileUpload(fileEntity);
        String uuid = fileEntity.getUuid();
        Optional<FileEntity> existFile = fileRepo.findByUuid(uuid);
        if (existFile.isPresent()) {
            throw new FileException(FILE_UPLOAD_INFO_ALREADY_EXIST, fileEntity.getOriginalFileName());
        }

        fileEntity.setUseYn(true);
        fileEntity.setCreatedBy(user.getId());
        fileEntity.setCreatedDate(TimeHelper.now());
        fileEntity.setDownloadCnt(0);
        fileEntity.setFileService(DEF_FILE_SERVICE_NAME);
        fileEntity.setFileServiceId(DEF_FILE_SERVICE_ID);
        fileEntity.setFileServiceType(FileServiceType.DEFAULT);
        fileEntity.setAccessAuthority(FileAuthorityType.PRIVATE);
        fileEntity.setFileExists(true);
        return fileRepo.save(fileEntity);
    }

    @Override
    @Transactional
    public List<FileEntity> createFileWithUpload(List<FileEntity> fileEntityList, User user) throws FileException {
        fileEntityList.forEach(this::validateFileUpload);
        List<String> uuidList = fileEntityList.stream().map(FileEntity::getUuid).toList();
        List<FileEntity> existFile = selectFileList(uuidList);
        if (!existFile.isEmpty()) {
            throw new FileException(FILE_UPLOAD_INFO_ALREADY_EXIST);
        }

        fileEntityList.forEach(fileEntity -> {
            fileEntity.setUseYn(true);
            fileEntity.setCreatedBy(user.getId());
            fileEntity.setCreatedDate(TimeHelper.now());
            fileEntity.setDownloadCnt(0);
            fileEntity.setFileService(DEF_FILE_SERVICE_NAME);
            fileEntity.setFileServiceId(DEF_FILE_SERVICE_ID);
            fileEntity.setFileServiceType(FileServiceType.DEFAULT);
            fileEntity.setAccessAuthority(FileAuthorityType.PRIVATE);
            fileEntity.setFileExists(true);
        });
        return fileRepo.saveAll(fileEntityList);
    }

    @Override
    @Transactional
    public void increaseFileDownloadCnt(FileEntity fileEntity) {
        fileEntity.setDownloadCnt(fileEntity.getDownloadCnt() + 1);
        fileRepo.save(fileEntity);
    }


    @Override
    @Transactional
    public void upsertFileServiceByUuid(String fileServiceName, Long fileServiceId, FileServiceType fileServiceType, FileAuthorityType authorityType, List<String> uuidList) {

        // 기존에 사용중인 파일이 있으면 사용안함으로 변경
        List<FileEntity> oldFileList = fileRepo.findByFileServiceAndFileServiceIdAndFileServiceType(fileServiceName, fileServiceId, fileServiceType);
        oldFileList.stream().filter(FileEntity::isUseYn).forEach(fileEntity -> {
            fileEntity.setUseYn(false);
            fileRepo.save(fileEntity);
//            entityManager.detach(fileEntity);
        });

        if (uuidList.isEmpty()) {
            return;
        }
        uuidList = uuidList.stream().distinct().toList();
        List<FileEntity> fileEntityList = fileRepo.findByUuidIn(uuidList);
        if (fileEntityList.isEmpty()) {
            return;
        }
        fileEntityList.stream()
                .filter(fileEntity -> fileEntity.getFileService() == null || fileEntity.getFileService().equals(DEF_FILE_SERVICE_NAME) || fileEntity.getFileService().equals(fileServiceName))
                .filter(fileEntity -> fileEntity.getFileServiceId() == null || fileEntity.getFileServiceId().equals(DEF_FILE_SERVICE_ID) || fileEntity.getFileServiceId().equals(fileServiceId))
                .forEach(fileEntity -> {
                    fileEntity.setUseYn(true);
                    fileEntity.setFileService(fileServiceName);
                    fileEntity.setFileServiceId(fileServiceId);
                    fileEntity.setFileServiceType(fileServiceType);
                    fileEntity.setAccessAuthority(authorityType);
                });
        fileRepo.saveAll(fileEntityList);
    }

    @Override
    @Transactional
    public void deleteServiceFile(String fileServiceName, Long fileServiceId) {
        List<FileEntity> fileEntityList = selectServiceFileList(fileServiceName, fileServiceId);
        fileEntityList.forEach(fileEntity -> fileEntity.setUseYn(false));
        fileRepo.saveAll(fileEntityList);
    }

    @Override
    @Transactional
    public void deleteServiceFile(String fileServiceName, Long fileServiceId, FileServiceType fileServiceType) {
        List<FileEntity> fileEntityList = selectServiceFileList(fileServiceName, fileServiceId, fileServiceType);
        fileEntityList.forEach(fileEntity -> fileEntity.setUseYn(false));
        fileRepo.saveAll(fileEntityList);
    }


    @Override
    @Transactional
    public void deleteServiceFile(FileEntity fileEntity) {
        fileEntity.setUseYn(false);
        fileRepo.save(fileEntity);
    }

    @Override
    @Transactional
    public void deleteServiceFile(List<FileEntity> fileEntityList) {
        fileEntityList.forEach(fileEntity -> fileEntity.setUseYn(false));
        fileRepo.saveAll(fileEntityList);
    }


    @Override
    @Transactional
    public void upsertFileExists(List<String> uuidList) {
        fileRepo.clearFileExists();
        try (Stream<FileEntity> fileEntityStream = fileRepo.streamByUuidIn(uuidList)) {
            fileEntityStream.forEach(fileEntity -> {
                fileEntity.setFileExists(true);
                fileRepo.save(fileEntity);
            });
        }
    }
}

