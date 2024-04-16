package xyz.needpainkiller.helper;

import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;
import xyz.needpainkiller.base.file.error.FileException;

import java.util.List;
import java.util.Objects;

import static xyz.needpainkiller.base.file.error.FileErrorCode.FILE_INVALID_NAME_FAILED;
import static xyz.needpainkiller.base.file.error.FileErrorCode.FILE_RESTRICT_EXTENSION;


@UtilityClass
public class FileHelper {

    public static String getFileExtension(MultipartFile file) throws FileException {
        return getFileExtension(file.getOriginalFilename());
    }

    public static String getFileExtension(String fileName) throws FileException {
        int lastIndexOf = Objects.requireNonNull(fileName).lastIndexOf(".");
        if (lastIndexOf == -1) {
            throw new FileException(FILE_INVALID_NAME_FAILED, fileName);
        }
        return fileName.substring(lastIndexOf);
    }

    public static void checkExtensionRestrict(List<String> restrictList, String fileExtension) {
        restrictList.forEach(restrictExtension -> {
            if (fileExtension.equalsIgnoreCase(restrictExtension)) {
                throw new FileException(FILE_RESTRICT_EXTENSION, String.format("%s 파일은 제한된 파일 형식입니다. ", fileExtension));
            }
        });
    }
}
