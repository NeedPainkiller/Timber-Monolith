package xyz.needpainkiller.api.file;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import xyz.needpainkiller.api.file.model.FileEntity;
import xyz.needpainkiller.base.authentication.AuthenticationService;
import xyz.needpainkiller.base.file.FileService;
import xyz.needpainkiller.base.file.error.FileException;
import xyz.needpainkiller.base.file.model.FileAuthorityType;
import xyz.needpainkiller.base.user.RoleService;
import xyz.needpainkiller.base.user.UserService;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.common.controller.CommonController;
import xyz.needpainkiller.helper.HttpHelper;
import xyz.needpainkiller.lib.security.error.TokenValidFailedException;
import xyz.needpainkiller.lib.storage.LocalStorageService;
import xyz.needpainkiller.lib.storage.SecureStorageService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static xyz.needpainkiller.base.file.error.FileErrorCode.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileController extends CommonController implements FileApi {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private FileService<FileEntity> fileService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private LocalStorageService<FileEntity> localStorageService;
    @Autowired
    private SecureStorageService<FileEntity> secureStorageService;

    public ResponseEntity<Map<String, Object>> uploadFile(final HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        List<FileEntity> fileList = localStorageService.upload(request);
        User user = authenticationService.getUserByToken(request);
        fileList = fileService.createFileWithUpload(fileList, user);
        model.put(KEY_FILE_LIST, fileList);
        return ok(model);
    }


    public void downloadFile(String uuid, HttpServletRequest request, HttpServletResponse response) {
        FileEntity file = fileService.selectFile(uuid);
        fileService.validateFile(file);

        FileAuthorityType fileAuthorityType = file.getAccessAuthority();

        try {
            if (!fileAuthorityType.equals(FileAuthorityType.PUBLIC)) {
                User requester = authenticationService.getUserByToken(request);
                List<Role> requesterRoles = roleService.selectRolesByUser(requester);
                if (!roleService.hasAdminRole(requesterRoles)) {
                    fileService.validateAuthority(file, requester);
                }
            }
            localStorageService.download(file, request, response);
            fileService.increaseFileDownloadCnt(file);
        } catch (TokenValidFailedException e) {
            if (FileAuthorityType.LOGON.equals(fileAuthorityType)) {
                throw new FileException(FILE_DOWNLOAD_FILE_NEED_AUTH);
            } else {
                throw e;
            }
        } catch (IOException e) {
            throw new FileException(FILE_DOWNLOAD_FAILED, e.getMessage());
        }
    }

    public ResponseEntity<Map<String, Object>> uploadSecureFile(final HttpServletRequest request) {
        boolean isMultipart = HttpHelper.isMultipartContent(request);
        if (!isMultipart) {
            throw new FileException(FILE_UPLOAD_IS_NOT_MULTIPART);
        }
        Map<String, Object> model = new HashMap<>();
        User user = authenticationService.getUserByToken(request);
        List<FileEntity> fileList = secureStorageService.upload(request);
        fileList = fileService.createFileWithUpload(fileList, user);
        model.put(KEY_FILE_LIST, fileList);
        return ok(model);
    }

    public void downloadSecureFile(String uuid, HttpServletRequest request, HttpServletResponse response) {
        FileEntity file = fileService.selectFile(uuid);
        fileService.validateFile(file);

        FileAuthorityType fileAuthorityType = file.getAccessAuthority();
        try {
            if (!fileAuthorityType.equals(FileAuthorityType.PUBLIC)) {
                User requester = authenticationService.getUserByToken(request);
                List<Role> requesterRoles = roleService.selectRolesByUser(requester);
                if (!roleService.hasAdminRole(requesterRoles)) {
                    fileService.validateAuthority(file, requester);
                }
            }
            secureStorageService.download(file, request, response);
            fileService.increaseFileDownloadCnt(file);

        } catch (TokenValidFailedException e) {
            if (FileAuthorityType.LOGON.equals(fileAuthorityType)) {
                throw new FileException(FILE_DOWNLOAD_FILE_NEED_AUTH);
            } else {
                throw e;
            }
        } catch (IOException e) {
            throw new FileException(FILE_DOWNLOAD_FAILED, e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Map<String, Object>> uploadAnonymousFile(HttpServletRequest request) {
        boolean isMultipart = HttpHelper.isMultipartContent(request);
        if (!isMultipart) {
            throw new FileException(FILE_UPLOAD_IS_NOT_MULTIPART);
        }
        Map<String, Object> model = new HashMap<>();

        User user = userService.selectSystemUser();
        List<FileEntity> fileList = secureStorageService.upload(request);
        fileList = fileService.createFileWithUpload(fileList, user);

        model.put(KEY_FILE_LIST, fileList);

        return ok(model);
    }
    /**
     * Constant for HTTP POST method.
     */

}
