package xyz.needpainkiller.api.authentication;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xyz.needpainkiller.api.authentication.dto.AuthenticationRequests;

import java.util.Map;

@Tag(name = "0. 로그인 & 인증", description = "AUTHENTICATION")
@RequestMapping(value = "/api/v1/authentication", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface AuthenticationApi {

    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    @Operation(description = "로그인 (Token 발급)")
    ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthenticationRequests.LoginRequest param, HttpServletRequest request, HttpServletResponse response);


    @GetMapping(value = "/logout")
    @Operation(description = "로그아웃 (Token 만료 처리)", hidden = true)
    ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response);

    @GetMapping(value = "/refresh")
    @Operation(description = "Token 재발급")
    ResponseEntity<Map<String, Object>> refreshToken(HttpServletRequest request, HttpServletResponse response);

    @GetMapping(value = "/check")
    @Operation(description = "Token 유효확인")
    ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request);
}
