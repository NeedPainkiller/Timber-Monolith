package xyz.needpainkiller.base.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationRequests {


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class LoginRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 8668879724817258669L;

        @Schema(description = "테넌트 ID", example = "1", required = true)
        private Long tenantPk;

        @NotBlank
        @Schema(description = "유저 ID, E-Mail 형식", example = "test", required = true)
        private String userId;
        @NotBlank
        @Schema(description = "패스워드(10자 이상, 1개 이상의 숫자 혹은 패스워드)", example = "test1234", required = true)
        private String userPwd;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class ResetPasswordRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = -4786481397946733100L;
        @NotBlank
        @Schema(description = "인증용 UUID", example = "e39a320d-79c7-47a6-842a-faaf0b85f108!", required = true)
        private String uuid;
        @NotBlank
        @Schema(description = "패스워드(10자 이상, 1개 이상의 숫자 혹은 패스워드)", example = "Rainbow2021!", required = true)
        private String password;
    }
}
