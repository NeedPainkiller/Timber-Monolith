package xyz.needpainkiller.base.file;

import org.apache.logging.log4j.util.Strings;
import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.base.file.error.FileException;
import xyz.needpainkiller.base.file.model.File;
import xyz.needpainkiller.base.file.model.FileAuthorityType;
import xyz.needpainkiller.base.file.model.FileServiceType;
import xyz.needpainkiller.base.user.model.User;

import java.util.List;
import java.util.function.Predicate;

import static xyz.needpainkiller.base.file.error.FileErrorCode.FILE_UPLOAD_INFO_ALREADY_EXIST;
import static xyz.needpainkiller.base.file.error.FileErrorCode.FILE_UPLOAD_INFO_NOT_EXIST;

public interface FileService <T extends File> {
    //    private static final int SQL_WHERE_IN_LIMIT = 2000;
    static final String DEF_FILE_SERVICE_NAME = "DEFAULT";
    static final Long DEF_FILE_SERVICE_ID = -1L;

    static final Predicate<File> availableFile = File::isUseYn;

    @Transactional
    T selectFile(String uuid);

    @Transactional
    List<T> selectFileList(List<String> uuidList);

    @Transactional
    List<T> selectFileNotUsed();

    @Transactional
    List<T> selectServiceFileList(String fileServiceName);

    @Transactional
    List<T> selectServiceFileList(String fileServiceName, Long fileServiceId);

    @Transactional
    List<T> selectServiceFileList(String fileServiceName, Long fileServiceId, FileServiceType fileServiceType);

    void validateFile(T file);

    void validateAuthority(T file, User requester);

    default void validateFileUpload(T file) throws FileException {
        if (file == null) {
            throw new FileException(FILE_UPLOAD_INFO_NOT_EXIST);
        } else {
            String uuid = file.getUuid();
            String originalFileName = file.getOriginalFileName();
            if (Strings.isBlank(uuid) || Strings.isBlank(originalFileName)) { // 업로드 정보 존재하지 않음
                throw new FileException(FILE_UPLOAD_INFO_NOT_EXIST);
            }
            Long filePk = file.getId();
            if (filePk != null) {
                throw new FileException(FILE_UPLOAD_INFO_ALREADY_EXIST);
            }
        }
    }

    @Transactional
    T createFileWithUpload(T file, User user) throws FileException;

    @Transactional
    List<T> createFileWithUpload(List<T> fileList, User user) throws FileException;

    @Transactional
    void increaseFileDownloadCnt(T file);

    @Transactional
    void upsertFileServiceByUuid(String fileServiceName, Long fileServiceId, FileServiceType fileServiceType, FileAuthorityType authorityType, List<String> uuidList);

    @Transactional
    void deleteServiceFile(String fileServiceName, Long fileServiceId);

    @Transactional
    void deleteServiceFile(String fileServiceName, Long fileServiceId, FileServiceType fileServiceType);

    @Transactional
    void deleteServiceFile(T file);

    @Transactional
    void deleteServiceFile(List<T> fileList);

    @Transactional
    void upsertFileExists(List<String> uuidList);
}
