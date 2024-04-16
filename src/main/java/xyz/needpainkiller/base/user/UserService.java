package xyz.needpainkiller.base.user;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.api.team.model.TeamEntity;
import xyz.needpainkiller.api.user.dao.UserSpecification;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.api.user.model.UserEntity;
import xyz.needpainkiller.base.team.model.Team;
import xyz.needpainkiller.base.tenant.model.Tenant;
import xyz.needpainkiller.base.user.dto.UserProfile;
import xyz.needpainkiller.base.user.dto.UserRequests;
import xyz.needpainkiller.base.user.dto.UserRequests.UpsertUserRequest;
import xyz.needpainkiller.base.user.error.UserException;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.common.dto.SearchCollectionResult;

import java.util.List;
import java.util.Map;

public interface UserService<U extends User, R extends Role, T extends Team> {
    U selectSystemUser();

    Long selectSystemUserPk();

    U selectUser(Long userPk) throws UserException;

    List<U> selectUserByUserId(String userId) throws UserException;

    U selectUserByUserId(Tenant tenant, String userId) throws UserException;

    U selectUserByUserId(Long tenantPk, String userId) throws UserException;

    boolean isUserIdExist(Long tenantPk, String userId);

    UserProfile<U, R, T> selectUserProfile(Long userPk) throws UserException;
    UserProfile<U, R, T> selectUserProfile(U user) throws UserException ;

    List<U> selectUserList();

    List<U> selectUserList(Long tenantPk);

    List<U> selectUserListByPkList(List<Long> userPkList);

    List<U> selectUserListByRole(Long rolePk);

    List<U> selectUserListByRoleList(List<Role> roleList);

    List<U> selectUserListByIdLike(String userId);

    List<U> selectUserListBySuperAdminRole();


    List<UserProfile<U, R, T>> mapUserProfileListByUserPkList(List<Long> userPkList);

    List<UserProfile<U, R, T>> mapUserProfileList(List<UserEntity> userList);

    Map<Long, U> selectUserMap();

    Map<Long, U> selectUserMapByPkList(List<Long> userPkList);

    SearchCollectionResult<U> selectUserList(UserRequests.SearchUserRequest param);


    SearchCollectionResult<UserProfile<U, R, T>> selectUserProfileList(UserRequests.SearchUserRequest param) ;
    @Transactional
    void increaseLoginFailedCnt(Long userPk);

    @Transactional
    void updateLastLoginDate(Long userPk);

    @Transactional
    U createUser(UpsertUserRequest param, List<R> roleList, U requester) throws UserException;

    @Transactional
    U updateUser(Long userPk, UpsertUserRequest param, List<R> roleList, U requester);

    @Transactional
    void updatePassword(Long userPk, Long requesterPk, String userPwd);

    @Transactional
    void deleteUser(Long tenantPk, Long userPk, U requester);

    @Transactional
    void enableUser(Long userPk);
}
