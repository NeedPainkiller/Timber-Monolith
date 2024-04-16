package xyz.needpainkiller.lib.storage;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import xyz.needpainkiller.base.file.error.FileException;
import xyz.needpainkiller.helper.FileHelper;
import xyz.needpainkiller.helper.HttpHelper;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static xyz.needpainkiller.base.file.error.FileErrorCode.*;

/**
 * Local File 저장소 서비스
 *
 * @author ywkang
 */

@Slf4j
@Service
public class SecureStorageService<T extends xyz.needpainkiller.base.file.model.File> extends DefaultStorageService<T> {
    private final SecretKey fileCipherSecretKey;
    private final Cipher fileCipher;
    private final List<String> restrictList;


    @Autowired
    public SecureStorageService(
            @Autowired ServletContext context,
            @Value("${file.scope}") String filePathScope,
            @Value("${file.path}") String filePath,
            @Value("${file.size-usable-limit}") long sizeUsableLimit,
            @Value("${file.size-upload-limit}") long sizeUploadLimit,
            @Value("${file.extension-restrict}") List<String> restrictList,
            @Autowired SecretKey fileCipherSecretKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(context, filePathScope, filePath, sizeUsableLimit, sizeUploadLimit);
        this.restrictList = restrictList;
        this.fileCipherSecretKey = fileCipherSecretKey;
        this.fileCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    }

    @Override
    public synchronized List<T> upload(HttpServletRequest request) {
        boolean isMultipart = HttpHelper.isMultipartContent(request);
        if (!isMultipart) {
            throw new FileException(FILE_UPLOAD_IS_NOT_MULTIPART);
        }
        List<T> uuidList = new ArrayList<>();

        try {
            Collection<Part> parts = request.getParts();
            validateParts(parts);
            mkdir();
            for (Part part : parts) {
                String filename = part.getSubmittedFileName();
                T uploadInfo = generateFileInfo(filename);
                String changedFileName = uploadInfo.getChangedFileName();

                FileHelper.checkExtensionRestrict(restrictList, uploadInfo.getFileType());

                File file = new File(realPathToSaved + changedFileName);

                fileCipher.init(Cipher.ENCRYPT_MODE, fileCipherSecretKey);

                try (InputStream fileInputStream = part.getInputStream();
                     FileOutputStream fileOutputStream = new FileOutputStream(file);
                     CipherOutputStream cipherOutputStream = new CipherOutputStream(fileOutputStream, fileCipher)) {
                    fileOutputStream.write(fileCipher.getIV());

                    byte[] buffer = new byte[8192];
                    int bytesRead;
                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        cipherOutputStream.write(buffer, 0, bytesRead);
                    }
                }

                uploadInfo.setFileSize(file.exists() ? file.length() : 0);
                uuidList.add(uploadInfo);
            }
            return uuidList;
        } catch (ServletException | IOException e) {
            log.error("file upload failed : {} | {}", e.getClass(), e.getMessage());
            this.remove(uuidList);
            throw new FileException(FILE_SECURE_UPLOAD_FAILED, e.getMessage());
        } catch (InvalidKeyException e) {
            log.error(e.getMessage());
            throw new FileException(FILE_ENCRYPT_FAILED, e.getMessage());
        }
    }

    @Override
    public synchronized void download(T fileInfo, HttpServletRequest request, HttpServletResponse response) throws IOException {

        String changedFileName = fileInfo.getChangedFileName();
        String originalFileName = fileInfo.getOriginalFileName().trim();

        File file = new File(realPathToSaved + changedFileName);
        if (!file.exists()) {
            throw new FileException(FILE_DOWNLOAD_FILE_NOT_EXIST);
        }
        String fileName = URLEncoder.encode(originalFileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

        response.setHeader("Content-Disposition",
                "attachment; filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1) + ";");
        try (FileInputStream fis = new FileInputStream(file)) {

            byte[] fileIv = new byte[16];
            int valid = fis.read(fileIv);
            if (valid == -1) {
                throw new FileException(FILE_SECURE_DOWNLOAD_FAILED, "IV is Blank");
            }
            fileCipher.init(Cipher.DECRYPT_MODE, fileCipherSecretKey, new IvParameterSpec(fileIv));
            try (
                    CipherInputStream cipherIn = new CipherInputStream(fis, fileCipher);
                    BufferedInputStream bis = new BufferedInputStream(cipherIn);
                    ServletOutputStream sos = response.getOutputStream();
                    BufferedOutputStream bos = new BufferedOutputStream(sos)
            ) {
                byte[] buff = new byte[2048];
                int bytesRead;
                while ((bytesRead = bis.read(buff)) != -1) {
                    bos.write(buff, 0, bytesRead);
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new FileException(FILE_SECURE_DOWNLOAD_FAILED, e.getMessage());
        } catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
            log.error(e.getMessage());
            throw new FileException(FILE_DECRYPT_FAILED, e.getMessage());
        }
    }
}
