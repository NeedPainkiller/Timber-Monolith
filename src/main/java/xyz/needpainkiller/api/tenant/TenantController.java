package xyz.needpainkiller.api.tenant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import xyz.needpainkiller.api.authentication.model.ApiEntity;
import xyz.needpainkiller.api.authentication.model.DivisionEntity;
import xyz.needpainkiller.api.authentication.model.MenuEntity;
import xyz.needpainkiller.api.team.model.TeamEntity;
import xyz.needpainkiller.api.tenant.model.TenantEntity;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.api.user.model.UserEntity;
import xyz.needpainkiller.api.user.model.UserRoleMapEntity;
import xyz.needpainkiller.base.authentication.AuthenticationService;
import xyz.needpainkiller.base.authentication.AuthorizationService;
import xyz.needpainkiller.base.tenant.TenantService;
import xyz.needpainkiller.base.tenant.dto.TenantRequests;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.tenant.model.Tenant;
import xyz.needpainkiller.base.user.RoleService;
import xyz.needpainkiller.base.user.UserService;
import xyz.needpainkiller.base.user.dto.UserProfile;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.common.controller.CommonController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static xyz.needpainkiller.base.tenant.error.TenantErrorCode.TENANT_CONFLICT;

@Slf4j
@RestController
@RequiredArgsConstructor
public class TenantController extends CommonController implements TenantApi {
    @Autowired
    private TenantService<TenantEntity> tenantService;
    @Autowired
    private AuthenticationService<UserEntity, RoleEntity> authenticationService;
    @Autowired
    private AuthorizationService<DivisionEntity, MenuEntity, ApiEntity, RoleEntity> authorizationService;
    @Autowired
    private UserService<UserEntity, RoleEntity, TeamEntity> userService;
    @Autowired
    private RoleService<RoleEntity, UserRoleMapEntity> roleService;

    @Override
    public ResponseEntity<Map<String, Object>> selectPublicTenantList(String userId, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        List<TenantEntity> tenantList = tenantService.selectPublicTenantList();
        if (userId != null && !userId.isEmpty()) {
            List<UserEntity> userList = userService.selectUserListByIdLike(userId);
            List<Long> filteredTenantPkList = userList.stream().map(User::getTenantPk).toList();
            tenantList = tenantList.stream().filter(tenant -> filteredTenantPkList.contains(tenant.getId())).toList();
        }
        model.put(KEY_LIST, tenantList);
        return ok(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> selectSwitchableTenantList(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        List<Long> switchableTenantPkList = authenticationService.getTenantPkListByToken(request);
        List<TenantEntity> tenantList = tenantService.selectTenantList();
        List<TenantEntity> switchableTenantList = tenantList.stream().filter(tenant -> switchableTenantPkList.contains(tenant.getId())).toList();
        model.put(KEY_LIST, switchableTenantList);
        return ok(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> selectTenantList(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();

        Long tenantPk = authenticationService.getTenantPkByToken(request);
        List<TenantEntity> tenantList = tenantService.selectTenantList();
        List<RoleEntity> authority = authenticationService.getRoleListByToken(request);
        if (!roleService.hasSystemAdminRole(authority)) {
            tenantList = tenantList.stream().filter(tenant -> tenant.filterByTenant(tenantPk)).toList();
        }
        model.put(KEY_LIST, tenantList);
        return ok(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> selectTenant(Long tenantPk, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        Tenant tenant = tenantService.selectTenant(tenantPk);
        model.put(KEY_RESULT, tenant);
        return ok(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> createTenant(TenantRequests.CreateTenantRequest param, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();
        List<RoleEntity> authority = authenticationService.getRoleListByToken(request);
        if (!roleService.hasSystemAdminRole(authority)) {
            return status(HttpStatus.FORBIDDEN).body(model);
        }
        User requester = authenticationService.getUserByToken(request);
        Tenant tenant = tenantService.createTenant(param, requester);

        model.put(KEY_RESULT, tenant);
        return ok(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> updateTenant(Long tenantPk, TenantRequests.UpdateTenantRequest param, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();

        List<RoleEntity> authority = authenticationService.getRoleListByToken(request);
        if (!roleService.hasSystemAdminRole(authority)) {
            Long userTenantPk = authenticationService.getTenantPkByToken(request);
            if (!tenantPk.equals(userTenantPk)) {
                throw new TenantException(TENANT_CONFLICT);
            }
        }

        User requester = authenticationService.getUserByToken(request);
        Tenant tenant = tenantService.updateTenant(tenantPk, param, requester);

        model.put(KEY_RESULT, tenant);
        return ok(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> deleteTenant(Long tenantPk, HttpServletRequest request) {
        Map<String, Object> model = new HashMap<>();

        List<RoleEntity> authority = authenticationService.getRoleListByToken(request);
        if (!roleService.hasSystemAdminRole(authority)) {
            Long userTenantPk = authenticationService.getTenantPkByToken(request);
            if (!tenantPk.equals(userTenantPk)) {
                throw new TenantException(TENANT_CONFLICT);
            }
        }
        User requester = authenticationService.getUserByToken(request);
        tenantService.deleteTenant(tenantPk, requester);

        return status(HttpStatus.NO_CONTENT).body(model);
    }

    @Override
    public ResponseEntity<Map<String, Object>> switchTenant(Long tenantPk, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = new HashMap<>();
        UserEntity requester = authenticationService.getUserByToken(request);

        List<Long> switchableTenantPkList = authenticationService.getTenantPkListByToken(request);
        if (switchableTenantPkList.stream().noneMatch(pk -> pk.equals(tenantPk))) {
            return status(HttpStatus.FORBIDDEN).body(model);
        }

        Long userPk = requester.getId();
        requester = userService.selectUser(userPk);
        try {
            UserProfile<UserEntity, RoleEntity, TeamEntity> userProfile = userService.selectUserProfile(requester);
            model.put(KEY_USER, requester);
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
        } finally {
            model.put(KEY_TOKEN, authenticationService.createToken(request, response, requester));
        }
        return ok(model);
    }

}