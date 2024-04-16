package xyz.needpainkiller.base.authentication;

import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import xyz.needpainkiller.base.authentication.error.ApiException;
import xyz.needpainkiller.lib.security.error.TokenValidFailedException;

import java.util.function.Supplier;

public interface AuthorizationChecker extends AuthenticationTrustResolver, AuthorizationManager<RequestAuthorizationContext> {
    boolean isAnonymous(Authentication authentication);

    boolean isRememberMe(Authentication authentication);

    AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext context) throws TokenValidFailedException, ApiException;
}
