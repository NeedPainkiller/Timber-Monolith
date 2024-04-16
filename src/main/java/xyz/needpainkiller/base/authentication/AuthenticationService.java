package xyz.needpainkiller.base.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.user.error.UserException;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.SecurityUser;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.List;

public interface AuthenticationService<U extends User, R extends Role> extends UserDetailsService {
    String createToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication);

    String createToken(HttpServletRequest request, HttpServletResponse response, U user);

    boolean isExpireSoon(HttpServletRequest request);

    String refreshToken(HttpServletRequest request, HttpServletResponse response);

    String getTokenNonValidate(HttpServletRequest request);

    void validateToken(HttpServletRequest request);

    void validateToken(String token);

    U getUserByToken(HttpServletRequest request);

    U getUserByToken(String token);

    Long getTenantPkByToken(HttpServletRequest request);

    Long getTenantPkByToken(String token);

    Long getUserPkByToken(HttpServletRequest request);

    Long getUserPkByToken(String token);

    List<R> getRoleListByToken(HttpServletRequest request);

    List<R> getRoleListByToken(String token);

    List<Long> getTenantPkListByToken(HttpServletRequest request);

    List<Long> getTenantPkListByToken(String token);

    void logout(HttpServletRequest request, HttpServletResponse response);

    Authentication getAuthentication(HttpServletRequest request);

    Authentication getAuthentication(String token);

    ErrorCode getAuthenticationExceptionType(U user, AuthenticationException e);

    SecurityUser loadUserByUsername(String userDataString) throws UsernameNotFoundException, UserException, TenantException;
}
