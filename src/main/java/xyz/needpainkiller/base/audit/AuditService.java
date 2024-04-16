package xyz.needpainkiller.base.audit;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.scheduling.annotation.Async;
import xyz.needpainkiller.base.user.dto.UserProfile;
import xyz.needpainkiller.common.model.HttpMethod;

import javax.annotation.Nullable;

public interface AuditService {
    @Async("asyncJobExecutor")
    void insertAuditLog(HttpMethod httpMethod, int statusCode,
                        String requestURI, String requestIp, String userAgent,
                        String requestContentType, String requestPayload,
                        String responseContentType, String responsePayload,
                        @Nullable String token);

    @Async("asyncJobExecutor")
    void insertLoginAuditLog(HttpServletRequest request, UserProfile<?,?,?> userProfile);
}
