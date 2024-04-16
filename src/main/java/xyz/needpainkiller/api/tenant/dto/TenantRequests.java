package xyz.needpainkiller.api.tenant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import xyz.needpainkiller.lib.validation.NonSpecialCharacter;

import java.io.Serial;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TenantRequests {
    private static String resolveUrl(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        } else {
            return url;
        }
    }

    public interface UpsertTenantRequest {
        void setUrl(String url);

        @Override
        boolean equals(Object o);

        @Override
        int hashCode();

        Boolean getVisibleYn();

        String getTitle();

        String getLabel();

        String getUrl();

        void setVisibleYn(Boolean visibleYn);

        void setTitle(String title);

        void setLabel(String label);

    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class CreateTenantRequest implements Serializable, UpsertTenantRequest {
        @Serial
        private static final long serialVersionUID = 1273927602993910119L;
        @NotNull
        @Schema(description = "노출 여부", example = "true / false", required = true)
        private Boolean visibleYn = true;
        private final Boolean defaultYn = false;
        @NotBlank
        @Schema(description = "테넌트 이름", example = "레인보우브레인2", required = true)
        private String title;
        @NotBlank
        @Schema(description = "라벨명", example = "Rainbow Brain2", required = true)
        private String label;
        @NotBlank
        @Schema(description = "회사 URL", example = "http://needpainkiller.co.kr", required = true)
        private String url;

        @NotBlank
        @NonSpecialCharacter
        @Schema(description = "유저 ID", example = "user01", required = true)
        private String userId;

        @NotBlank
        @NonSpecialCharacter
        @Schema(description = "유저 이름", example = "user01", required = true)
        private String userName;

        @Schema(description = "패스워드(10자 이상, 1개 이상의 숫자 혹은 패스워드)", example = "password!", required = true)
        private String userPwd;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class UpdateTenantRequest implements Serializable, UpsertTenantRequest {
        @Serial
        private static final long serialVersionUID = -3449786007747414238L;
        @NotNull
        @Schema(description = "공개 여부", example = "true / false", required = true)
        private Boolean visibleYn = true;
        @NotNull
        @Schema(description = "기본 테넌트 여부", example = "true / false", required = true)
        private Boolean defaultYn = false;
        @NotBlank
        @Schema(description = "회사 이름", example = "레인보우브레인2", required = true)
        private String title;
        @NotBlank
        @Schema(description = "라벨명", example = "Rainbow Brain2", required = true)
        private String label;
        @NotBlank
        @Schema(description = "회사 URL", example = "http://needpainkiller.co.kr", required = true)
        private String url;

    }
}