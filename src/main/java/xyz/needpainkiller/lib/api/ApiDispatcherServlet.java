package xyz.needpainkiller.lib.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.DispatcherServlet;
import xyz.needpainkiller.api.audit.AuditService;
import xyz.needpainkiller.common.model.HttpMethod;
import xyz.needpainkiller.helper.HttpHelper;
import xyz.needpainkiller.lib.security.provider.JsonWebTokenProvider;

@Slf4j
public class ApiDispatcherServlet extends DispatcherServlet {
    private static final String CONTENT_TYPE_JSON = "application/json";
    @Value("${api.path-prefix}")
    private String API_PREFIX;

    private final AuditService auditService;

    public ApiDispatcherServlet(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    protected void doDispatch(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) throws Exception {
        log.debug("###### ApiDispatcherServlet > doDispatch start");

        super.doDispatch(request, response);
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith(API_PREFIX)) {
            log(request, response);
        }
    }

    private void log(HttpServletRequest request, HttpServletResponse response) {
        if (request == null || response == null) {
            log.error("insertAuditLog - request or response is null");
            return;
        }

        int statusCode = response.getStatus();
        HttpMethod httpMethod = HttpMethod.nameOf(request.getMethod());
        boolean methodHasPayload = HttpMethod.hasPayload(httpMethod);

        String requestURI = request.getRequestURI();
        String userAgent = request.getHeader("user-agent");
        String requestIp = HttpHelper.getClientIP(request);

        String requestContentType = request.getContentType();
        String requestPayload = null;
        if (!Strings.isBlank(requestContentType)) {
            if (methodHasPayload && requestContentType.startsWith(CONTENT_TYPE_JSON)) {
                requestPayload = HttpHelper.getRequestPayload(request);
            }
        }
        String responseContentType = response.getContentType();
        String responsePayload;
        if (!Strings.isBlank(responseContentType) && responseContentType.startsWith(CONTENT_TYPE_JSON)) {
            responsePayload = HttpHelper.getResponsePayload(response);
        } else {
            responsePayload = null;
        }
        String token = request.getHeader(JsonWebTokenProvider.BEARER_TOKEN_HEADER);
        auditService.insertAuditLog(httpMethod, statusCode,
                requestURI, requestIp, userAgent,
                requestContentType, requestPayload,
                responseContentType, responsePayload, token);
    }
}
