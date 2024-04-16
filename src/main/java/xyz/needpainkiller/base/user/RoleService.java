package xyz.needpainkiller.base.user;

import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.base.user.dto.RoleRequests;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.base.user.model.UserRoleMap;
import xyz.needpainkiller.common.dto.SearchCollectionResult;

import java.util.List;

public interface RoleService<T extends Role, R extends UserRoleMap> {
    Long SUPER_ADMIN = 1L;

    boolean isRoleExist(Long rolePk);

    boolean isRoleExist(Long tenantPk, String roleNm);

    List<T> selectAll();

    T selectRoleByRolePk(Long rolePk);

    T selectRoleByRoleNm(String roleNm);

    SearchCollectionResult<T> selectRoleList(RoleRequests.SearchRoleRequest param);

    List<R> selectUserRoleMap();

    List<R> selectUserRoleMap(List<Long> userPkList);

    List<Long> selectUserPkListByRolePk(Long rolePk);

    List<Long> selectUserPkListByRolePkList(List<Long> rolePkList);

    List<T> selectRolesByUser(User user);

    List<T> selectRolesByUser(Long userPk);

    List<T> selectRolesByPkList(List<Long> rolePkList);

    List<T> selectRolesByNameList(List<String> roleNmList);

    List<String> selectAuthorityByUser(User user);

    List<String> selectAuthorityByUser(Long userPk);

    boolean hasSystemAdminRole(List<T> roleList);

    boolean hasAdminRole(List<T> roleList);

    boolean hasEditableRole(List<T> roleList);

    boolean isEditableAuthority(User user, Long ownerUserPk);

    boolean isEditableAuthority(User user, List<T> userRoles, Long ownerUserPk);

    void checkRequestRoleAuthority(List<T> requestRoleList, List<T> authority);

    @Transactional
    T createRole(RoleRequests.UpsertRoleRequest param, User requester);

    @Transactional
    T updateRole(Long rolePk, RoleRequests.UpsertRoleRequest param, User requester);

    @Transactional
    void deleteRole(Long tenantPk, Long rolePk, User requester);

    @Transactional
    void upsertUserRole(Long userPk, List<T> roleList);

    @Transactional
    void deleteUserRole(Long userPk);
}
