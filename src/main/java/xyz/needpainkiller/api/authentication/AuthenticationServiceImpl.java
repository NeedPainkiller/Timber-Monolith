package xyz.needpainkiller.api.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.needpainkiller.api.team.model.TeamEntity;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.api.user.model.UserEntity;
import xyz.needpainkiller.api.user.model.UserRoleMapEntity;
import xyz.needpainkiller.base.authentication.AuthenticationService;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.user.RoleService;
import xyz.needpainkiller.base.user.UserService;
import xyz.needpainkiller.base.user.error.RoleException;
import xyz.needpainkiller.base.user.error.UserException;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.SecurityUser;
import xyz.needpainkiller.base.user.model.UserStatusType;
import xyz.needpainkiller.lib.exceptions.ErrorCode;
import xyz.needpainkiller.lib.security.error.TokenValidFailedException;
import xyz.needpainkiller.lib.security.provider.JsonWebTokenProvider;

import java.util.List;

import static xyz.needpainkiller.base.tenant.error.TenantErrorCode.TENANT_SEARCH_EMPTY;
import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.*;

@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService<UserEntity, RoleEntity> {

    @Autowired
    private UserService<UserEntity, RoleEntity, TeamEntity> userService;
    @Autowired
    private RoleService<RoleEntity, UserRoleMapEntity> roleService;
    @Autowired
//    @Qualifier("headerJsonWebTokenProvider")
    private JsonWebTokenProvider<UserEntity, RoleEntity> jsonWebTokenProvider;

    @Override
    public String createToken(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SecurityUser<UserEntity, RoleEntity> securityUser = (SecurityUser) authentication.getPrincipal();
        return createToken(request, response, securityUser.getUser());
    }

    @Override
    public String createToken(HttpServletRequest request, HttpServletResponse response, UserEntity user) {
        List<RoleEntity> roleList = roleService.selectRolesByUser(user);
        return jsonWebTokenProvider.createToken(request, response, user, roleList);
    }

    @Override
    public boolean isExpireSoon(HttpServletRequest request) {
        return jsonWebTokenProvider.isExpireSoon(request);
    }

    @Override
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        jsonWebTokenProvider.validateToken(request);
        Authentication authentication = getAuthentication(request);
        SecurityUser<UserEntity, RoleEntity> securityUser = (SecurityUser<UserEntity, RoleEntity>) authentication.getPrincipal();
        UserEntity user = securityUser.getUser();
        return createToken(request, response, user);
    }

    @Override
    public String getTokenNonValidate(HttpServletRequest request) {
        return jsonWebTokenProvider.resolveToken(request);
    }

    @Override
    public void validateToken(HttpServletRequest request) {
        jsonWebTokenProvider.validateToken(request);
    }

    @Override
    public void validateToken(String token) {
        jsonWebTokenProvider.validateToken(token);
    }

    @Override
    public UserEntity getUserByToken(HttpServletRequest request) {
        String token = jsonWebTokenProvider.resolveToken(request);
        return getUserByToken(token);
    }

    @Override
    public UserEntity getUserByToken(String token) {
        jsonWebTokenProvider.validateToken(token);
        Authentication authentication = getAuthentication(token);
        SecurityUser<UserEntity, RoleEntity> securityUser = (SecurityUser<UserEntity, RoleEntity>) authentication.getPrincipal();
        return securityUser.getUser();
    }

    @Override
    public Long getTenantPkByToken(HttpServletRequest request) {
        String token = jsonWebTokenProvider.resolveToken(request);
        return getTenantPkByToken(token);
    }

    @Override
    public Long getTenantPkByToken(String token) {
        jsonWebTokenProvider.validateToken(token);
        Long tenantPk;
        try {
            tenantPk = jsonWebTokenProvider.getTenantPk(token);
        } catch (TokenValidFailedException e) {
            tenantPk = getUserByToken(token).getTenantPk();
        }
        return tenantPk;
    }

    @Override
    public Long getUserPkByToken(HttpServletRequest request) {
        String token = jsonWebTokenProvider.resolveToken(request);
        return getUserPkByToken(token);
    }

    @Override
    public Long getUserPkByToken(String token) {
        jsonWebTokenProvider.validateToken(token);
        Long userPk;
        try {
            userPk = jsonWebTokenProvider.getUserPk(token);
        } catch (TokenValidFailedException | UserException e) {
            userPk = getUserByToken(token).getId();
        }
        return userPk;
    }

    @Override
    public List<RoleEntity> getRoleListByToken(HttpServletRequest request) {
        String token = jsonWebTokenProvider.resolveToken(request);
        return getRoleListByToken(token);
    }

    @Override
    public List<RoleEntity> getRoleListByToken(String token) {
        jsonWebTokenProvider.validateToken(token);
        List<String> authorityList = jsonWebTokenProvider.getRole(token);
        return roleService.selectRolesByNameList(authorityList);
    }

    @Override
    public List<Long> getTenantPkListByToken(HttpServletRequest request) {
        String token = jsonWebTokenProvider.resolveToken(request);
        return getTenantPkListByToken(token);
    }

    @Override
    public List<Long> getTenantPkListByToken(String token) {
        jsonWebTokenProvider.validateToken(token);
        List<RoleEntity> authorityList = getRoleListByToken(token);
        return authorityList.stream().map(Role::getTenantPk).toList();
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        jsonWebTokenProvider.expireToken(request, response);
    }


    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        return getAuthentication(jsonWebTokenProvider.resolveToken(request));
    }

    @Override
    public Authentication getAuthentication(String token) {
        jsonWebTokenProvider.validateToken(token);
        Long tenantPk = jsonWebTokenProvider.getTenantPk(token);
        String userId = jsonWebTokenProvider.getUserId(token);

        String loginData = String.format("%d/%s", tenantPk, userId);

        SecurityUser securityUser = loadUserByUsername(loginData);
        return new UsernamePasswordAuthenticationToken(securityUser, "", securityUser.getAuthorities());
    }

    @Override
    public ErrorCode getAuthenticationExceptionType(UserEntity user, AuthenticationException e) {
        log.info("getAuthenticationExceptionType : {}", e.getMessage());
        if (user == null) {
            return USER_NOT_EXIST;
        }
        if (e instanceof BadCredentialsException) {
            return LOGIN_PASSWORD_NOT_MATCH;
        } else if (e instanceof InternalAuthenticationServiceException) {
            return USER_NOT_EXIST;
        } else if (e instanceof DisabledException) {
            UserStatusType userStatusType = user.getUserStatus();
            return switch (userStatusType) {
                case NOT_VERIFIED, WITHDRAWAL -> VERIFICATION_NOT_VERIFIED;
                case LOCKED -> USER_LOCKED;
                default -> USER_DISABLED;
            };
        } else if (e instanceof CredentialsExpiredException) {
            return PASSWORD_EXPIRED;
        }
        return LOGIN_FAILED_UNKNOWN;
    }


    @Override
    public SecurityUser<UserEntity, RoleEntity> loadUserByUsername(String userDataString) throws UsernameNotFoundException, UserException, TenantException {
        if (userDataString == null || userDataString.isEmpty()) {
            throw new UserException(LOGIN_REQUEST_MISSING);
        }
        try {
            int index = userDataString.indexOf("/");
            String tenant = userDataString.substring(0, index);
            Long tenantPk = Long.parseLong(tenant);
            String userId = userDataString.substring(index + 1);

            UserEntity user = userService.selectUserByUserId(tenantPk, userId);
            List<RoleEntity> roleList = roleService.selectRolesByUser(user);
            return new SecurityUser<>(user, roleList);
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            throw new TenantException(TENANT_SEARCH_EMPTY);
        } catch (UserException | RoleException e) {
            log.error(e.getMessage());
            throw new UserException(USER_NOT_EXIST);
        }
    }
}
