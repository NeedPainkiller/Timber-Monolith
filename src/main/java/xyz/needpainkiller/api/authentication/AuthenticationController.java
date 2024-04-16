package xyz.needpainkiller.api.authentication;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RestController;
import xyz.needpainkiller.api.authentication.model.ApiEntity;
import xyz.needpainkiller.api.authentication.model.DivisionEntity;
import xyz.needpainkiller.api.authentication.model.MenuEntity;
import xyz.needpainkiller.api.team.model.TeamEntity;
import xyz.needpainkiller.api.tenant.model.TenantEntity;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.api.user.model.UserEntity;
import xyz.needpainkiller.base.audit.AuditService;
import xyz.needpainkiller.base.authentication.AuthenticationService;
import xyz.needpainkiller.base.authentication.AuthorizationService;
import xyz.needpainkiller.base.authentication.dto.AuthenticationRequests;
import xyz.needpainkiller.base.authentication.error.LoginException;
import xyz.needpainkiller.base.tenant.TenantService;
import xyz.needpainkiller.base.tenant.model.Tenant;
import xyz.needpainkiller.base.user.UserService;
import xyz.needpainkiller.base.user.dto.UserProfile;
import xyz.needpainkiller.base.user.model.SecurityUser;
import xyz.needpainkiller.common.controller.CommonController;
import xyz.needpainkiller.lib.exceptions.ErrorCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.LOGIN_REQUEST_MISSING;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController extends CommonController implements AuthenticationApi {

    @Autowired
    private AuditService auditService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthenticationService<UserEntity, RoleEntity> authenticationService;
    @Autowired
    private AuthorizationService<DivisionEntity, MenuEntity, ApiEntity, RoleEntity> authorizationService;
    @Autowired
    private UserService<UserEntity, RoleEntity, TeamEntity> userService;
    @Autowired
    private TenantService<TenantEntity> tenantService;

    public ResponseEntity<Map<String, Object>> login(AuthenticationRequests.LoginRequest param, HttpServletRequest request, HttpServletResponse response) throws LoginException {

        Long tenantPk = param.getTenantPk();
        Tenant tenant;

        if (tenantPk != null) {
            tenant = tenantService.selectTenant(tenantPk);
        } else {
            tenant = tenantService.selectDefatultTenant();
        }
        String userId = param.getUserId();
        String userPwd = param.getUserPwd();
        if (Strings.isBlank(userId) || Strings.isBlank(userPwd)) {
            throw new LoginException(LOGIN_REQUEST_MISSING);
        }
        userId = userId.trim();
        userPwd = userPwd.trim();

        SecurityUser<UserEntity, RoleEntity> securityUser;
        UserEntity user = userService.selectUserByUserId(tenant, userId);

        try {
            String loginData = String.format("%d/%s", tenantPk, userId);
            log.info("loginData: {}", loginData);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginData, userPwd);
            log.info("token: {}", token);
            Authentication authentication = authenticationManager.authenticate(token);
            log.info("authentication: {}", authentication);
            securityUser = (SecurityUser) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            userService.increaseLoginFailedCnt(user.getId());
            ErrorCode errorCode = authenticationService.getAuthenticationExceptionType(user, e);
            throw new LoginException(errorCode, e.getMessage());
        }
        user = securityUser.getUser();
        Map<String, Object> model = new HashMap<>();
        try {
            UserProfile<UserEntity, RoleEntity, TeamEntity> userProfile = userService.selectUserProfile(user);
            model.put(KEY_USER, user);
            TeamEntity team = userProfile.getTeam();
            model.put(KEY_TEAM, team);
            List<RoleEntity> roleList = userProfile.getRoleList();
            model.put(KEY_ROLE_LIST, roleList);
            List<DivisionEntity> divisionList = authorizationService.selectDivisionByRoleList(roleList);
            model.put(KEY_MENU_LIST, divisionList);
            List<ApiEntity> apiList = authorizationService.selectApiListByRoleList(roleList);
            model.put(KEY_API_LIST, apiList);
            List<ApiEntity> publicApiList = authorizationService.selectPublicApiList();
            model.put(KEY_PUBLIC_API_LIST, publicApiList);

            auditService.insertLoginAuditLog(request, userProfile);
        } finally {
            model.put(KEY_TOKEN, authenticationService.createToken(request, response, user));
            userService.updateLastLoginDate(user.getId());
        }
        return ok(model);
    }

    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.logout(request, response);
        return ok().build();
    }

    public ResponseEntity<Map<String, Object>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = authenticationService.refreshToken(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put(KEY_TOKEN, refreshToken);
        return ok(model);
    }

    public ResponseEntity<Map<String, Object>> validateToken(HttpServletRequest request) {
        authenticationService.validateToken(request);
        return ok().build();
    }
}
