package xyz.needpainkiller.api.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.api.user.dao.RoleRepo;
import xyz.needpainkiller.api.user.dao.RoleSpecification;
import xyz.needpainkiller.api.user.dao.UserRoleMapRepo;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.api.user.model.UserRoleMapEntity;
import xyz.needpainkiller.base.authentication.AuthorizationService;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.user.RoleService;
import xyz.needpainkiller.base.user.dto.RoleRequests;
import xyz.needpainkiller.base.user.error.RoleException;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.common.dto.SearchCollectionResult;
import xyz.needpainkiller.helper.TimeHelper;
import xyz.needpainkiller.helper.ValidationHelper;

import java.util.List;

import static xyz.needpainkiller.base.tenant.error.TenantErrorCode.TENANT_CONFLICT;
import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.*;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService<RoleEntity, UserRoleMapEntity> {

    @Autowired
    private AuthorizationService authorizationService;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRoleMapRepo userRoleMapRepo;

    @Override
    public boolean isRoleExist(Long rolePk) {
        return roleRepo.findAll().stream().filter(Role::isAvailable).map(Role::getId).anyMatch(integer -> integer.equals(rolePk));
    }


    @Override
    public boolean isRoleExist(Long tenantPk, String roleNm) {
        return roleRepo.findAll().stream()
                .filter(role -> role.filterByTenant(tenantPk))
                .filter(Role::isAvailable)
                .map(Role::getRoleName)
                .anyMatch(name -> name.equals(roleNm));
    }

    @Override
    @Cacheable(value = "RoleList", key = "'selectAll'")
    public List<RoleEntity> selectAll() {
        return roleRepo.findAll().stream().filter(Role::isAvailable).toList();
    }

    @Override
    @Cacheable(value = "Role", key = "'selectRoleByRolePk-' + #rolePk")
    public RoleEntity selectRoleByRolePk(Long rolePk) {
        return roleRepo.findById(rolePk).orElseThrow(() -> new RoleException(ROLE_NOT_EXIST));
    }

    @Override
    @Cacheable(value = "Role", key = "'selectRoleByRoleNm-' + #roleNm")
    public RoleEntity selectRoleByRoleNm(String roleNm) {
        return roleRepo.findAll().stream().filter(Role::isAvailable).filter(role -> role.getRoleName().equals(roleNm.trim())).findAny().orElseThrow(() -> new RoleException(ROLE_NOT_EXIST));
    }

    @Override
    public SearchCollectionResult<RoleEntity> selectRoleList(RoleRequests.SearchRoleRequest param) {
        Specification<RoleEntity> specification = Specification.where(RoleSpecification.search(param));
        Page<RoleEntity> rolePage = roleRepo.findAll(specification, param.pageOf());
        List<RoleEntity> roleList = rolePage.getContent();
        long total = rolePage.getTotalElements();
        return SearchCollectionResult.<RoleEntity>builder().collection(roleList).foundRows(total).build();
    }

    @Override
    public List<UserRoleMapEntity> selectUserRoleMap() {
        return userRoleMapRepo.findAll();
    }

    @Override
    public List<UserRoleMapEntity> selectUserRoleMap(List<Long> userPkList) {
        userPkList = userPkList.stream().distinct().toList();
        return userRoleMapRepo.findByUserPkIn(userPkList);
    }

    @Override
    @Cacheable(value = "UserRole", key = "'selectUserPkListByRolePk-' + #rolePk")
    public List<Long> selectUserPkListByRolePk(Long rolePk) {
        return userRoleMapRepo.findByRolePk(rolePk).stream().map(UserRoleMapEntity::getUserPk).toList();
    }

    @Override
    @Cacheable(value = "UserRole", key = "'selectUserPkListByRolePkList-' + #rolePkList.hashCode()")
    public List<Long> selectUserPkListByRolePkList(List<Long> rolePkList) {
        rolePkList = rolePkList.stream().distinct().toList();
        return userRoleMapRepo.findByRolePkIn(rolePkList).stream().map(UserRoleMapEntity::getUserPk).toList();
    }


    @Override
    @Cacheable(value = "RoleList", key = "'selectRolesByUser-' + #user.getId()")

    public List<RoleEntity> selectRolesByUser(User user) {
        return selectRolesByUser(user.getId());
    }

    @Override
    @Cacheable(value = "RoleList", key = "'selectRolesByUser-' + #userPk")

    public List<RoleEntity> selectRolesByUser(Long userPk) {
        List<Long> rolePkList = userRoleMapRepo.findByUserPk(userPk).stream().map(UserRoleMapEntity::getRolePk).distinct().toList();
        return roleRepo.findByIdIn(rolePkList);
    }

    @Override
    @Cacheable(value = "RoleList", key = "'selectRolesByPkList-' + #rolePkList.hashCode()")
    public List<RoleEntity> selectRolesByPkList(List<Long> rolePkList) {
        List<RoleEntity> roleList = roleRepo.findAll().stream().filter(Role::isAvailable).filter(role -> rolePkList.contains(role.getId())).toList();
        if (roleList.isEmpty()) {
            throw new RoleException(ROLE_NOT_EXIST);
        }
        return roleList;
    }

    @Override
    @Cacheable(value = "RoleList", key = "'selectRolesByNameList-' + #roleNmList.hashCode()")
    public List<RoleEntity> selectRolesByNameList(List<String> roleNmList) {
        return roleRepo.findAll().stream().filter(Role::isAvailable).filter(role -> roleNmList.contains(role.getRoleName())).toList();
    }

    @Override
    @Cacheable(value = "RoleAuthorityList", key = "'selectAuthorityByUser-' + #user.getId()")
    public List<String> selectAuthorityByUser(User user) {
        return selectAuthorityByUser(user.getId());
    }

    @Override
    @Cacheable(value = "RoleAuthorityList", key = "'selectAuthorityByUser-' + #userPk")
    public List<String> selectAuthorityByUser(Long userPk) {
        List<RoleEntity> roles = selectRolesByUser(userPk);
        return roles.stream().map(Role::getAuthority).toList();
    }

    @Override
    public boolean hasSystemAdminRole(List<RoleEntity> roleList) {
        return roleList.stream().anyMatch(Role::isSystemAdmin);
    }

    @Override
    public boolean hasAdminRole(List<RoleEntity> roleList) {
        return roleList.stream().anyMatch(Role::isAdmin);
    }

    @Override
    public boolean hasEditableRole(List<RoleEntity> roleList) {
        return roleList.stream().anyMatch(Role::isEditable);
    }

    @Override
    public boolean isEditableAuthority(User user, Long ownerUserPk) {
        List<RoleEntity> userRoles = selectRolesByUser(user);
        return isEditableAuthority(user, userRoles, ownerUserPk);
    }


    @Override
    public boolean isEditableAuthority(User user, List<RoleEntity> userRoles, Long ownerUserPk) {
        if (!hasAdminRole(userRoles)) { //관리자 권한 아님
            if (ownerUserPk != null && ownerUserPk != 0) {
                return user.getId().equals(ownerUserPk); // 본인 맞음
            }
        }
        return true; // 관리자 권한임
    }

    @Override
    public void checkRequestRoleAuthority(List<RoleEntity> requestRoleList, List<RoleEntity> authority) {
        if (!hasAdminRole(authority) && hasAdminRole(requestRoleList)) {// Admin 권한이 없으나, Admin 을 추가하려는 경우
            throw new RoleException(ROLE_CAN_NOT_MANAGE_ADMIN);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserRole", allEntries = true), @CacheEvict(value = "Role", allEntries = true), @CacheEvict(value = "RoleList", allEntries = true), @CacheEvict(value = "RoleAuthorityList", allEntries = true)})
    public RoleEntity createRole(RoleRequests.UpsertRoleRequest param, User requester) {
        String roleName = param.getName();

        Long requesterPk = requester.getId();
        Long tenantPk = param.getTenantPk();

        ValidationHelper.checkAnyRequiredEmpty(roleName);
        if (isRoleExist(tenantPk, roleName)) {
            throw new RoleException(ROLE_ALREADY_EXIST);
        }

        RoleEntity role = new RoleEntity();
        role.setTenantPk(tenantPk);
        role.setUseYn(true);
        role.setRoleName(roleName);
        role.setRoleDescription(param.getDescription());
        role.setSystemAdmin(false);
        role.setAdmin(param.getIsAdmin());
        role.setEditable(true);
        role.setCreatedBy(requesterPk);
        role.setUpdatedBy(requesterPk);
        role = roleRepo.save(role);
        authorizationService.upsertApiRole(role, param.getApiList());
        return role;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserRole", allEntries = true), @CacheEvict(value = "Role", allEntries = true), @CacheEvict(value = "RoleList", allEntries = true), @CacheEvict(value = "RoleAuthorityList", allEntries = true)})
    public RoleEntity updateRole(Long rolePk, RoleRequests.UpsertRoleRequest param, User requester) {
        RoleEntity role = selectRoleByRolePk(rolePk);

        Long requesterPk = requester.getId();
        Long tenantPk = param.getTenantPk();

        if (!role.isUseYn()) {
            throw new RoleException(ROLE_DELETED);
        }
        if (!role.isEditable()) {
            throw new RoleException(ROLE_CAN_NOT_EDITABLE);
        }
        if (!role.getTenantPk().equals(tenantPk)) {
            throw new TenantException(TENANT_CONFLICT);
        }

        role.setTenantPk(tenantPk);
        role.setUseYn(true);
        role.setRoleName(param.getName());
        role.setRoleDescription(param.getDescription());
        role.setSystemAdmin(false);
        role.setAdmin(param.getIsAdmin());
        role.setEditable(true);
        role.setUpdatedBy(requesterPk);
        role = roleRepo.save(role);
        authorizationService.upsertApiRole(role, param.getApiList());
        return role;
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserRole", allEntries = true), @CacheEvict(value = "Role", allEntries = true), @CacheEvict(value = "RoleList", allEntries = true), @CacheEvict(value = "RoleAuthorityList", allEntries = true)})
    public void deleteRole(Long tenantPk, Long rolePk, User requester) {
        Long requesterPk = requester.getId();
        RoleEntity role = selectRoleByRolePk(rolePk);
        if (!role.isUseYn()) {
            throw new RoleException(ROLE_DELETED);
        }
        if (!role.isEditable()) {
            throw new RoleException(ROLE_CAN_NOT_EDITABLE);
        }
        if (!role.getTenantPk().equals(tenantPk)) {
            throw new TenantException(TENANT_CONFLICT);
        }
        role.setUseYn(false);
        role.setUpdatedBy(requesterPk);
        role.setUpdatedDate(TimeHelper.now());
        roleRepo.save(role);
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserRole", allEntries = true), @CacheEvict(value = "Role", allEntries = true), @CacheEvict(value = "RoleList", allEntries = true), @CacheEvict(value = "RoleAuthorityList", allEntries = true)})
    public void upsertUserRole(Long userPk, List<RoleEntity> roleList) {
        this.deleteUserRole(userPk);
        if (roleList == null) return;
        roleList.forEach(role -> {
            UserRoleMapEntity userRoleMapEntity = new UserRoleMapEntity();
            userRoleMapEntity.setUserPk(userPk);
            userRoleMapEntity.setRolePk(role.getId());
            userRoleMapRepo.save(userRoleMapEntity);
        });
    }

    @Override
    @Transactional
    @Caching(evict = {@CacheEvict(value = "UserRole", allEntries = true), @CacheEvict(value = "Role", allEntries = true), @CacheEvict(value = "RoleList", allEntries = true), @CacheEvict(value = "RoleAuthorityList", allEntries = true)})
    public void deleteUserRole(Long userPk) {
        List<UserRoleMapEntity> userRoleMapEntityList = userRoleMapRepo.findByUserPk(userPk);
        userRoleMapRepo.deleteAll(userRoleMapEntityList);
    }
}
