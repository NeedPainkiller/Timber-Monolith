package xyz.needpainkiller.lib.storage;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.FileNameUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import xyz.needpainkiller.api.file.error.FileException;
import xyz.needpainkiller.api.file.model.Files;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static xyz.needpainkiller.api.file.error.FileErrorCode.*;

@Slf4j
public abstract class DefaultStorageService {
    private static final Predicate<String> judgeUUID = uuidStr -> {
        try {
            UUID.fromString(uuidStr);
            return true;
            //do something
        } catch (IllegalArgumentException exception) {
            return false;
        }
    };
    protected final long sizeUsableLimit;
    protected final long sizeUploadLimit;
    protected final String realPathToSaved;

    protected DefaultStorageService() {
        sizeUsableLimit = 0L;
        sizeUploadLimit = 0L;
        realPathToSaved = ".\\files\\";
    }


    protected DefaultStorageService(
            ServletContext context,
            String filePathScope,
            String filePath,
            long sizeUsableLimit,
            long sizeUploadLimit) {
        if (context == null) {
            throw new FileException(FILE_STORAGE_CONTEXT_INVALID);
        }
        this.sizeUsableLimit = sizeUsableLimit;
        this.sizeUploadLimit = sizeUploadLimit;
        if (filePathScope.equals("internal")) {
            this.realPathToSaved = context.getRealPath(filePath);
            mkdir();
            if (!isFileStorageCanSaved()) {
                throw new FileException(FILE_STORAGE_USABLE_SIZE_LIMIT);
            }
        } else {
            this.realPathToSaved = filePath;
        }
    }

    /**
     * 파일 저장경로 디렉터리 생성
     */
    public void mkdir() {
        File realPathToSavedDir = new File(realPathToSaved);
        if (!realPathToSavedDir.exists()) {
            realPathToSavedDir.mkdirs();
        }
    }

    /**
     * 실제 파일 존재여부 확인
     *
     * @param filePath 파일 경로
     * @return 파일존재 여부
     */
    public boolean isFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 파일 업로드 및 저장
     *
     * @param request HttpServletRequest 객체
     */
    abstract List<Files> upload(HttpServletRequest request);

    /**
     * 파일 다운로드
     *
     * @param file     파일 도메인 객체
     * @param request  HttpServletRequest 객체
     * @param response HttpServletResponse 객체
     */
    abstract void download(Files file, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public String getRealPathToSaved() {
        return realPathToSaved;
    }

    public long getUsableSpace() {
        long usableSpace;
        try {
            NumberFormat nf = NumberFormat.getNumberInstance();
            Path fileStorePath = FileSystems.getDefault().getPath(realPathToSaved);
            FileStore store = java.nio.file.Files.getFileStore(fileStorePath);
            log.info("UsableSpace | available= {}, total={}", nf.format(store.getUsableSpace()), nf.format(store.getTotalSpace()));
            usableSpace = store.getUsableSpace();
        } catch (IOException e) {
            log.error("error querying space: {}", e.getMessage());
            usableSpace = 0L;
        }
        return usableSpace;
    }

    public boolean isFileStorageCanSaved() {
        return sizeUsableLimit < getUsableSpace();
    }


    /**
     * 파일 복수 삭제
     * Async
     *
     * @param fileList 파일 도메인 객체 리스트
     * @return 삭제된 파일 리스트
     */
    public List<Files> remove(List<Files> fileList) {
        if (CollectionUtils.isEmpty(fileList)) {
            return new ArrayList<>();
        }
        return fileList.stream().filter(this::removeRealFile).toList();
    }

    /**
     * 파일 삭제
     * Async
     *
     * @param file 파일 도메인 객체
     * @return 삭제여부
     */
    public Boolean remove(Files file) {
        return removeRealFile(file);
    }

    private boolean removeRealFile(Files file) {
        String changedFileName = file.getChangedFileName();
        Path savedFile = FileSystems.getDefault().getPath(realPathToSaved, changedFileName);
        boolean exists = java.nio.file.Files.exists(savedFile);
        if (exists) {
            try {
                return java.nio.file.Files.deleteIfExists(savedFile);
            } catch (IOException e) {
                log.error("Failed Delete File IOException : {}", e.getMessage());
                return false;
            }
        } else {
            log.info("File Not Exists : {}", changedFileName);
            return true;
        }
    }

    public List<String> findAllExistFileUuid() {
        try {
            Path fileStorePath = FileSystems.getDefault().getPath(realPathToSaved);
            try (Stream<Path> stream = java.nio.file.Files.list(fileStorePath)) {
                List<String> allExistFile = stream
                        .filter(file -> !java.nio.file.Files.isDirectory(file))
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .map(FilenameUtils::removeExtension)
                        .filter(judgeUUID)
                        .toList();
                return allExistFile;
            }
        } catch (IOException e) {
            log.error("error querying space: {}", e.getMessage());
            return new ArrayList<>();
        }
    }

    protected void validateParts(Collection<Part> parts) throws FileException {
        parts.forEach(part -> {
            if (part.getSize() > sizeUploadLimit) {
                throw new FileException(FILE_UPLOAD_SIZE_LIMIT);
            }
            String filename = part.getSubmittedFileName();
            if (Strings.isBlank(filename)) {
                throw new FileException(FILE_INVALID_NAME_FAILED);
            }
        });
    }

    protected Files generateFileInfo(MultipartFile multipartFile) {
        Files file = generateFileInfo(multipartFile.getOriginalFilename());
        file.setFileSize(multipartFile.getSize());
        return file;
    }

    protected Files generateFileInfo(String fileName) {
        String uuid = UUID.randomUUID().toString();
        String fileExtension = FileNameUtils.getExtension(fileName);
        String changedFileName = uuid + '.' + fileExtension;

        Files file = new Files();
        file.setUuid(uuid);
        file.setFileType(fileExtension);
        file.setOriginalFileName(fileName);
        file.setChangedFileName(changedFileName);
        return file;
    }
}

