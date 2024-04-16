package xyz.needpainkiller.api.user;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.api.team.model.TeamEntity;
import xyz.needpainkiller.api.user.dao.UserRepo;
import xyz.needpainkiller.api.user.dao.UserSpecification;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.api.user.model.UserEntity;
import xyz.needpainkiller.api.user.model.UserRoleMapEntity;
import xyz.needpainkiller.base.team.TeamService;
import xyz.needpainkiller.base.team.error.TeamException;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.tenant.model.Tenant;
import xyz.needpainkiller.base.user.UserService;
import xyz.needpainkiller.base.user.dto.UserProfile;
import xyz.needpainkiller.base.user.dto.UserRequests.SearchUserRequest;
import xyz.needpainkiller.base.user.dto.UserRequests.UpsertUserRequest;
import xyz.needpainkiller.base.user.error.UserException;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.base.user.model.UserRoleMap;
import xyz.needpainkiller.base.user.model.UserStatusType;
import xyz.needpainkiller.common.dto.SearchCollectionResult;
import xyz.needpainkiller.helper.TimeHelper;
import xyz.needpainkiller.helper.ValidationHelper;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static xyz.needpainkiller.base.tenant.error.TenantErrorCode.TENANT_CONFLICT;
import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.USER_ALREADY_EXIST;
import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.USER_NOT_EXIST;

@Slf4j
@Service
public class UserServiceImpl implements UserService<UserEntity, RoleEntity, TeamEntity> {

    @Value("${rpa.system-user}")
    private Long SYSTEM_USER;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleServiceImpl roleServiceImpl;
    @Autowired
    private TeamService<TeamEntity> teamService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void init() {
        log.info("CommonUserService");
        log.info("SYSTEM_USER: {}", SYSTEM_USER);
    }

    @Override
    @Cacheable(value = "User", key = "'selectSystemUser'")
    public UserEntity selectSystemUser() {
        return selectUser(SYSTEM_USER);
    }

    @Override
    public Long selectSystemUserPk() {
        return SYSTEM_USER;
    }


    @Override
    public UserEntity selectUser(Long userPk) throws UserException {
        UserEntity user = userRepo.findUserById(userPk);
        if (user == null) {
            throw new UserException(USER_NOT_EXIST);
        }
        return user;
    }

    @Override
    public List<UserEntity> selectUserByUserId(String userId) throws UserException {
        userId = userId.trim();
        List<UserEntity> userList = userRepo.findUserByUserId(userId);
        if (userList == null || userList.isEmpty()) {
            throw new UserException(USER_NOT_EXIST);
        }
        return userList;
    }

    @Override
    public UserEntity selectUserByUserId(Tenant tenant, String userId) throws UserException {
        Long tenantPk = tenant.getId();
        return selectUserByUserId(tenantPk, userId);
    }

    @Override
    public UserEntity selectUserByUserId(Long tenantPk, String userId) throws UserException {
        userId = userId.trim();
        List<UserEntity> userList = userRepo.findUserByUserId(userId);
        if (userList == null || userList.isEmpty()) {
            throw new UserException(USER_NOT_EXIST);
        }
        return userList.stream().filter(User::isAvailable).filter(user -> user.filterByTenant(tenantPk)).findAny()
                .orElseThrow(() -> new UserException(USER_NOT_EXIST));
    }

    @Override
    public boolean isUserIdExist(Long tenantPk, String userId) {
        userId = userId.trim();
        List<UserEntity> userList = userRepo.findUserByUserId(userId);
        if (userList == null || userList.isEmpty()) {
            return false;
        }
        return userList.stream().filter(User::isAvailable).anyMatch(user -> user.getTenantPk().equals(tenantPk));
    }

    @Override
    @Cacheable(value = "UserProfile", key = "'selectUserProfile-' + #userPk", unless = "#result == null")
    public UserProfile<UserEntity, RoleEntity, TeamEntity> selectUserProfile(Long userPk) throws UserException {
        UserEntity user = userRepo.findUserById(userPk);
        if (user == null) {
            throw new UserException(USER_NOT_EXIST);
        }
        return selectUserProfile(user);
    }

    @Override
    @Cacheable(value = "UserProfile", key = "'selectUserProfile-' + #user.hashCode()", unless = "#result == null")
    public UserProfile<UserEntity, RoleEntity, TeamEntity> selectUserProfile(UserEntity user) throws UserException {
        Long userPk = user.getId();
        List<RoleEntity> userRoleList = roleServiceImpl.selectRolesByUser(userPk);
        Long teamPk = user.getTeamPk();
        TeamEntity team;
        try {
            team = teamService.selectTeam(teamPk);
        } catch (TeamException e) {
            team = null;
        }
        return new UserProfile<UserEntity, RoleEntity, TeamEntity>(user, team, userRoleList);
    }

    @Override
    @Cacheable(value = "UserList", key = "'selectUserList'")
    public List<UserEntity> selectUserList() {
        return userRepo.findAll();
    }

    @Override
    @Cacheable(value = "UserList", key = "'selectUserList-' + #tenantPk")
    public List<UserEntity> selectUserList(Long tenantPk) {
        return userRepo.findAll().stream().filter(user -> user.filterByTenant(tenantPk)).toList();
    }

    @Override
    @Cacheable(value = "UserList", key = "'selectUserListByPkList-' + #userPkList.hashCode()")
    public List<UserEntity> selectUserListByPkList(List<Long> userPkList) {
        return userRepo.findAllByIdIn(userPkList);
    }

    @Override
    @Cacheable(value = "UserList", key = "'selectUserListByRole-' + #rolePk")
    public List<UserEntity> selectUserListByRole(Long rolePk) {
        List<Long> userPkList = roleServiceImpl.selectUserPkListByRolePk(rolePk);
        return selectUserListByPkList(userPkList);
    }


    @Override
    @Cacheable(value = "UserList", key = "'selectUserListByRoleList-' + #roleList.hashCode()")
    public List<UserEntity> selectUserListByRoleList(List<Role> roleList) {
        List<Long> rolePkList = roleList.stream().map(Role::getId).toList();
        List<Long> userPkList = roleServiceImpl.selectUserPkListByRolePkList(rolePkList);
        return selectUserListByPkList(userPkList);
    }

    @Override
    public List<UserEntity> selectUserListByIdLike(String userId) {
        return selectUserList().stream().filter(User::isAvailable).filter(user -> user.getUserId().startsWith(userId)).toList();
    }


    @Override
    @Cacheable(value = "UserList", key = "'selectUserListBySuperAdminRole'")
    public List<UserEntity> selectUserListBySuperAdminRole() {
        return selectUserListByRole(RoleServiceImpl.SUPER_ADMIN);
    }


    @Override
    @Cacheable(value = "UserProfileList", key = "'mapUserProfileListByUserPkList-' + #userPkList.hashCode()")
    public List<UserProfile<UserEntity, RoleEntity, TeamEntity>> mapUserProfileListByUserPkList(List<Long> userPkList) {
        List<UserEntity> userList = selectUserListByPkList(userPkList);
        return mapUserProfileList(userList);
    }

    @Override
    @Cacheable(value = "UserProfileList", key = "'mapUserProfileList-' + #userList.hashCode()")
    public List<UserProfile<UserEntity, RoleEntity, TeamEntity>> mapUserProfileList(List<UserEntity> userList) {
        Map<Long, TeamEntity> teamMap = teamService.selectTeamMap();
        List<RoleEntity> roleList = roleServiceImpl.selectAll();
        List<UserRoleMapEntity> userRoleMapList = roleServiceImpl.selectUserRoleMap();
        return userList.stream()
                .map(user -> {
                    Long userPk = user.getId();
                    Long teamPk = user.getTeamPk();
                    TeamEntity team = teamMap.get(teamPk);
                    List<Long> rolePkList = userRoleMapList.stream()
                            .filter(userRoleMap -> userPk.equals(userRoleMap.getUserPk()))
                            .map(UserRoleMap::getRolePk).toList();
                    List<RoleEntity> userRoleList = roleList.stream().filter(role -> rolePkList.contains(role.getId())).toList();
                    return new UserProfile<>(user, team, userRoleList);
                }).toList();
    }

    @Override
    public Map<Long, UserEntity> selectUserMap() {
        List<UserEntity> userList = selectUserList();
        return userList.stream().collect(Collectors.toMap(User::getId, t -> t));
    }

    @Override
    public Map<Long, UserEntity> selectUserMapByPkList(List<Long> userPkList) {
        List<UserEntity> userList = selectUserListByPkList(userPkList);
        return userList.stream().collect(Collectors.toMap(User::getId, t -> t));
    }


    @Override
    public SearchCollectionResult<UserEntity> selectUserList(SearchUserRequest param) {
        Specification<UserEntity> specification = Specification.where(UserSpecification.search(param));
        Page<UserEntity> userPage = userRepo.findAll(specification, param.pageOf());
        List<UserEntity> userList = userPage.getContent();
        long total = userPage.getTotalElements();
        return SearchCollectionResult.<UserEntity>builder().collection(userList).foundRows(total).build();
    }

    @Override
    public SearchCollectionResult<UserProfile<UserEntity, RoleEntity, TeamEntity>> selectUserProfileList(SearchUserRequest param) {
        Specification<UserEntity> specification = Specification.where(UserSpecification.search(param));
        Page<UserEntity> userPage = userRepo.findAll(specification, param.pageOf());
        List<UserEntity> userList = userPage.getContent();
        List<UserProfile<UserEntity, RoleEntity, TeamEntity>> UserProfileList = mapUserProfileList(userList);
        long total = userPage.getTotalElements();
        return SearchCollectionResult.<UserProfile<UserEntity, RoleEntity, TeamEntity>>builder().collection(UserProfileList).foundRows(total).build();
    }


    @Override
    public void increaseLoginFailedCnt(Long userPk) {
        UserEntity user = selectUser(userPk);
        user.setLoginFailedCnt(user.getLoginFailedCnt() + 1);
        userRepo.save(user);
    }


    @Override
    public void updateLastLoginDate(Long userPk) {
        UserEntity user = selectUser(userPk);
        user.setLoginFailedCnt(0);
        user.setLastLoginDate(TimeHelper.now());
        userRepo.save(user);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "User", allEntries = true),
            @CacheEvict(value = "UserList", allEntries = true),
            @CacheEvict(value = "UserRole", allEntries = true)
    })
    public UserEntity createUser(UpsertUserRequest param, List<RoleEntity> roleList, UserEntity requester) throws UserException {
        Long requesterPk = requester.getId();
        Long tenantPk = param.getTenantPk();

        String userId = param.getUserId();
        String userEmail = param.getUserEmail();
        String userNm = param.getUserName();
        String userPwd = param.getUserPwd();

        Long teamPk = param.getTeamPk();
        teamService.selectTeam(teamPk);

        ValidationHelper.checkUserData(userId, userNm, userPwd);
        ValidationHelper.checkAnyRequiredEmpty(userEmail, userNm, userId);

        if (isUserIdExist(tenantPk, userId)) {
            throw new UserException(USER_ALREADY_EXIST);
        }

        if (roleList.stream().map(Role::getTenantPk).anyMatch(roleTenantPk -> !Objects.equals(roleTenantPk, tenantPk))) {
            throw new TenantException(TENANT_CONFLICT);
        }

        UserEntity user = new UserEntity();
        user.setTenantPk(tenantPk);
        user.setUseYn(true);
        user.setUserId(userId);
        user.setUserEmail(userEmail);
        user.setUserName(userNm);
        user.setUserPwd(bCryptPasswordEncoder.encode(userPwd));
        user.setTeamPk(teamPk);
        user.setUserStatus(param.getUserStatusType());
        user.setCreatedBy(requesterPk);
        user.setCreatedDate(TimeHelper.now());
        user.setUpdatedBy(requesterPk);
        user.setUpdatedDate(TimeHelper.now());
        user.setLoginFailedCnt(0);
        Map<String, Serializable> data = param.getData();
        user.setData(Objects.requireNonNullElseGet(data, HashMap::new));

        user = userRepo.save(user);
        roleServiceImpl.upsertUserRole(user.getId(), roleList);
        return user;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "User", allEntries = true),
            @CacheEvict(value = "UserList", allEntries = true),
            @CacheEvict(value = "UserRole", allEntries = true)
    })
    public UserEntity updateUser(Long userPk, UpsertUserRequest param, List<RoleEntity> roleList, UserEntity requester) {
        String userId = param.getUserId();
        String userEmail = param.getUserEmail();
        String userName = param.getUserName();
        String userPwd = param.getUserPwd();

        Long requesterPk = requester.getId();
        Long tenantPk = param.getTenantPk();

        Long teamPk = param.getTeamPk();
        teamService.selectTeam(teamPk);

        ValidationHelper.checkUserData(userId, userName);

        if (roleList.stream().map(Role::getTenantPk).anyMatch(roleTenantPk -> !Objects.equals(roleTenantPk, tenantPk))) {
            throw new TenantException(TENANT_CONFLICT);
        }

        UserEntity user = selectUser(userPk);

        if (!user.getTenantPk().equals(tenantPk)) {
            throw new TenantException(TENANT_CONFLICT);
        }

        user.setTenantPk(tenantPk);
        user.setUseYn(true);
        user.setUserId(userId);
        user.setUserEmail(userEmail);
        user.setUserName(userName);
        user.setTeamPk(teamPk);
        user.setUserStatus(param.getUserStatusType());
        user.setUpdatedBy(requesterPk);
        user.setUpdatedDate(TimeHelper.now());
        user.setLoginFailedCnt(0);
        user.setData(param.getData());
        boolean updatePassword = !Strings.isBlank(userPwd);
        if (updatePassword) {
            updatePassword(userPk, requesterPk, userPwd);
        }
        user = userRepo.save(user);
        roleServiceImpl.upsertUserRole(userPk, roleList);
        return user;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "User", allEntries = true),
            @CacheEvict(value = "UserList", allEntries = true),
            @CacheEvict(value = "UserRole", allEntries = true)
    })
    public void updatePassword(Long userPk, Long requesterPk, String userPwd) {
        ValidationHelper.checkPassword(userPwd);
        UserEntity user = selectUser(userPk);
        user.setUserPwd(bCryptPasswordEncoder.encode(userPwd));
        user.setUpdatedBy(requesterPk);
        user.setUpdatedDate(TimeHelper.now());
        userRepo.save(user);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "User", allEntries = true),
            @CacheEvict(value = "UserList", allEntries = true),
            @CacheEvict(value = "UserRole", allEntries = true)
    })
    public void deleteUser(Long tenantPk, Long userPk, UserEntity requester) {
        Long requesterPk = requester.getId();

        UserEntity user = selectUser(userPk);
        if (!user.getTenantPk().equals(tenantPk)) {
            throw new TenantException(TENANT_CONFLICT);
        }

        user.setUseYn(false);
        user.setUserId(user.getUserId() + "-" + UUID.randomUUID());
        user.setUserStatus(UserStatusType.NOT_USED);
        user.setUpdatedBy(requesterPk);
        user.setUpdatedDate(TimeHelper.now());
        userRepo.save(user);
        roleServiceImpl.deleteUserRole(userPk);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "User", allEntries = true),
            @CacheEvict(value = "UserList", allEntries = true),
            @CacheEvict(value = "UserRole", allEntries = true)
    })
    public void enableUser(Long userPk) {
        UserEntity user = selectUser(userPk);
        user.setUserStatus(UserStatusType.OK);
        user.setUpdatedBy(SYSTEM_USER);
        user.setUpdatedDate(TimeHelper.now());
        userRepo.save(user);
    }
}