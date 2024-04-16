package xyz.needpainkiller.api.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Tag(name = "10000000. 파일", description = "FILE")
@RequestMapping(value = "/api/v1/files", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface FileApi {
    @Operation(description = "파일 업로드")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, Object>> uploadFile(HttpServletRequest request);

    @Operation(description = "파일 다운로드")
    @GetMapping(value = "/download/{uuid:.+}")
    void downloadFile(@PathVariable("uuid") String uuid, HttpServletRequest request, HttpServletResponse response);

    @Operation(description = "보안 파일 업로드")
    @PostMapping(value = "/upload/secure", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, Object>> uploadSecureFile(HttpServletRequest request);

    @Operation(description = "보안 파일 다운로드")
    @GetMapping(value = "/download/secure/{uuid:.+}")
    void downloadSecureFile(@PathVariable("uuid") String uuid, HttpServletRequest request, HttpServletResponse response);

    @Operation(description = "익명 파일 업로드")
    @PostMapping(value = "/upload/anonymous", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Map<String, Object>> uploadAnonymousFile(HttpServletRequest request);
}