package xyz.needpainkiller.lib.api.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.CommonErrorCode;

import java.io.IOException;


/**
 * JwtAuthenticationFilter (/api/v1 일 경우)
 * ApiFilter
 * ApiDispatcherServlet
 * ApiInterceptor
 */
@Slf4j
@Component
@WebFilter
@Profile({"!prod"})
public class ApiDevFilter extends ApiFilter {
    public ApiDevFilter(
            @Value("${spring.base-url}") String baseUrl,
            @Value("${api.path-prefix}") String API_PREFIX,
            @Value("${resource.scope}") String resourcePathScope,
            @Value("${resource.path}") String resourcePathRoot) {
        super(baseUrl, API_PREFIX, resourcePathScope, resourcePathRoot);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            doFilter(httpServletRequest, httpServletResponse, chain);
        } catch (BusinessException e) {
            writeResponse(httpServletResponse, e.getErrorCode(), e);
        } catch (Exception e) {
            writeResponse(httpServletResponse, CommonErrorCode.INTERNAL_SERVER_ERROR, e);
        }
        log.debug("###### ApiDispatcherServlet > doDispatch end");
    }
}