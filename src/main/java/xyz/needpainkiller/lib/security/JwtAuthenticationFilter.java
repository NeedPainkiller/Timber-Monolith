package xyz.needpainkiller.lib.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import xyz.needpainkiller.api.authentication.AuthenticationService;
import xyz.needpainkiller.lib.exceptions.ApiErrorResponse;
import xyz.needpainkiller.lib.exceptions.BusinessException;
import xyz.needpainkiller.lib.exceptions.ErrorCode;
import xyz.needpainkiller.lib.security.error.TokenValidFailedException;

import java.io.IOException;
import java.io.PrintWriter;

import static xyz.needpainkiller.helper.HttpHelper.convertObjectToJson;

/**
 * JWT 값 검증 Filter
 * WebSecurityConfigurerAdapter 구현체에서 addFilterBefore 로 등록
 * AUTH_EVERYONE_WHITELIST 에 등록된 URI 도 해당 Filter 를 거치므로 Try-catch 하여 chain 을 수행할 수 있도록 처리해야함
 *
 * @author needpainkiller
 */

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final AuthenticationService authenticationService;

    public JwtAuthenticationFilter(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("## JwtAuthenticationFilter > doFilter start");

        try {
            /**
             * 검증된 Token 을 이용해 SecurityContextHolder 에 사용자 정보 저장
             * Token 이 유효하지 않을경우 Authentication 의 Principal 은 "anonymousUser" 로 생성된다.
             * */
            Authentication authentication = authenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("##### Authentication > {}", authentication.getPrincipal().toString());
        } catch (TokenValidFailedException e) {
            log.debug("##### Authentication > anonymousUser : {}/{}", e.getErrorCode(), e.getMessage());
        }

        try {
            /**
             * 이후 AuthorizationChecker 및 ServletDispatcher 로 체이닝 전달.
             * AuthorizationChecker 로 Authentication 객체가 전달된다.
             * */
            chain.doFilter(request, response);


            /**
             * JWT 자동 재발급 프로세스 > 보류
             */
/*            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            if (authenticationService.isExpireSoon(httpServletRequest)) {
                String token = authenticationService.refreshToken(httpServletRequest);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader(BEARER_TOKEN_HEADER, token);
                log.info("REFRESHED JWT : {}", token);
            } else {
                log.info("NOT REFRESHED JWT");
            }*/
        } catch (BusinessException e) {
            log.error(e.getMessage());
            log.error("Exception : {} ->", e.getClass().getName(), e);
            ErrorCode errorCode = e.getErrorCode();
            ApiErrorResponse apiErrorResponse = ApiErrorResponse.of(errorCode, e);
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setStatus(errorCode.getStatus().value());
            httpServletResponse.setContentType("text/json");
            httpServletResponse.setCharacterEncoding("UTF-8");
            PrintWriter writer = httpServletResponse.getWriter();
            writer.write(convertObjectToJson(apiErrorResponse));
            writer.flush();
        }
        log.debug("## JwtAuthenticationFilter > doFilter end");

    }
}
