package xyz.needpainkiller.api.tenant;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.api.tenant.dao.TenantRepo;
import xyz.needpainkiller.api.tenant.dto.TenantBootstrapRequests;
import xyz.needpainkiller.api.tenant.model.TenantEntity;
import xyz.needpainkiller.base.bootstrap.Bootstrap;
import xyz.needpainkiller.base.team.TeamService;
import xyz.needpainkiller.base.team.dto.TeamRequests;
import xyz.needpainkiller.base.team.model.Team;
import xyz.needpainkiller.base.tenant.TenantService;
import xyz.needpainkiller.base.tenant.dto.TenantRequests;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.tenant.model.Tenant;
import xyz.needpainkiller.base.user.RoleService;
import xyz.needpainkiller.base.user.UserService;
import xyz.needpainkiller.base.user.dto.UserRequests;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.base.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static xyz.needpainkiller.base.tenant.error.TenantErrorCode.*;


@Slf4j
@Service
public class TenantServiceImpl implements TenantService<TenantEntity>, Bootstrap {
    //    private static final Predicate<Tenant> predicateAvailableTenant = Tenant::isActive;
//    private static final Predicate<Tenant> predicatePublicTenant = Tenant::isPublic;
//    private static final Predicate<Tenant> predicateDefaultTenant = Tenant::isDefault;
    private static final String HTTP_REGEX = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    private static final Pattern HTTP_PATTERN = Pattern.compile(HTTP_REGEX);
    @Autowired
    private TenantRepo tenantRepo;
    @Autowired
    private StringEncryptor stringEncryptor;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private TeamService teamService;

    @Override
    @Cacheable(value = "TenantList", key = "'selectTenantList'")
    public List<TenantEntity> selectTenantList() {
        return tenantRepo.findAll().stream().filter(predicateAvailableTenant).toList();
    }

    @Override
    @Cacheable(value = "TenantList", key = "'selectPublicTenantList'")
    public List<TenantEntity> selectPublicTenantList() {
        return tenantRepo.findAll().stream().filter(predicatePublicTenant).toList();
    }

    @Override
    public Map<Long, TenantEntity> selectTenantMap() {
        return tenantRepo.findAll().stream().filter(predicateAvailableTenant).collect(Collectors.toMap(Tenant::getId, t -> t));
    }

    @Override
    public List<Long> selectTenantPkList() {
        return tenantRepo.findAll().stream().filter(predicateAvailableTenant).map(Tenant::getId).toList();
    }

    @Override
    @Cacheable(value = "Tenant", key = "'selectTenant' + #tenantPk", unless = "#result == null")
    public TenantEntity selectTenant(Long tenantPk) {
        return tenantRepo.findById(tenantPk)
                .filter(predicateAvailableTenant).orElseThrow(() -> new TenantException(TENANT_NOT_EXIST));
    }

    @Override
    @Cacheable(value = "Tenant", key = "'selectDefatultTenant'", unless = "#result == null")
    public TenantEntity selectDefatultTenant() {
        return tenantRepo.findAll().stream().filter(predicateDefaultTenant).findFirst().orElse(null);
    }


    public void checkTenantParam(TenantRequests.UpsertTenantRequest param) {
        String title = param.getTitle();
        if (title == null || title.isBlank()) {
            throw new TenantException(TENANT_TITLE_BLANK);
        }
        String url = param.getUrl();
        if (url == null || url.isBlank()) {
            throw new TenantException(TENANT_SERVER_URL_BLANK);
        }
        if (!TenantServiceImpl.HTTP_PATTERN.matcher(url).matches()) {
            throw new TenantException(TENANT_SERVER_URL_PATTERN_NOT_MATCH);
        }
    }

    public void checkDuplicateTenant(Tenant updateTenant, TenantRequests.UpsertTenantRequest param) {
        String title = param.getTitle().trim();
        String url = param.getUrl().trim();

        List<TenantEntity> tenantList = selectTenantList();
        List<TenantEntity> sameServerUrlTenantLists = tenantList.stream().filter(t -> t.getUrl().equals(url)).toList();
        if (sameServerUrlTenantLists.isEmpty()) {
            return;
        }
        List<TenantEntity> sameCompanyTenantLists = tenantList.stream().filter(t -> t.getTitle().equals(title)).toList();

        TenantServiceImpl.log.info("sameCompanyTenantLists : {}", sameCompanyTenantLists);
        TenantServiceImpl.log.info("sameServerUrlTenantLists : {}", sameServerUrlTenantLists);

        boolean hasSameCompany = false;
        boolean hasSameServerUrl = false;
        if (updateTenant == null) { // 신규 tenant 등록
            TenantServiceImpl.log.info("create Tenant");
            hasSameCompany = !sameCompanyTenantLists.isEmpty();
            hasSameServerUrl = !sameServerUrlTenantLists.isEmpty();
        } else { // 기존 tenant 업데이트
            TenantServiceImpl.log.info("update Tenant");
            hasSameCompany = sameCompanyTenantLists.stream().anyMatch(tenant -> !updateTenant.getId().equals(tenant.getId()));
            hasSameServerUrl = sameServerUrlTenantLists.stream().anyMatch(tenant -> !updateTenant.getId().equals(tenant.getId()));
        }
        if (hasSameCompany) {
            throw new TenantException(TENANT_TITLE_ALREADY_EXIST);
        }
        if (hasSameServerUrl) {
            throw new TenantException(TENANT_SERVER_URL_ALREADY_EXIST);
        }
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Tenant", allEntries = true),
            @CacheEvict(value = "TenantList", allEntries = true)
    })
    public TenantEntity createTenant(TenantRequests.CreateTenantRequest param, User requester) {
        checkTenantParam(param);
        checkDuplicateTenant(null, param);

        Long requesterPk = requester.getId();

        TenantEntity tenant = new TenantEntity();
        tenant.setDefaultYn(false);
        tenant.setUseYn(true);
        tenant.setVisibleYn(param.getVisibleYn());

        tenant.setTitle(param.getTitle().trim());
        tenant.setLabel(param.getLabel().trim());
        tenant.setUrl(param.getUrl().trim());

        tenant.setCreatedBy(requesterPk);
        tenant.setUpdatedBy(requesterPk);
        tenant = tenantRepo.save(tenant);
        createTenantBootstrapSet(tenant, param, requester);
        return tenant;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Tenant", allEntries = true),
            @CacheEvict(value = "TenantList", allEntries = true)
    })
    public TenantEntity updateTenant(Long tenantPk, TenantRequests.UpdateTenantRequest param, User requester) {
        TenantEntity tenant = selectTenant(tenantPk);

        if (tenant == null) {
            throw new TenantException(TENANT_NOT_EXIST);
        }

        checkTenantParam(param);
        checkDuplicateTenant(tenant, param);

        if (!tenant.isUseYn()) {
            throw new TenantException(TENANT_DELETED);
        }

        Long requesterPk = requester.getId();
        tenant.setDefaultYn(false);
        tenant.setUseYn(true);
        tenant.setVisibleYn(param.getVisibleYn());

        tenant.setTitle(param.getTitle().trim());
        tenant.setLabel(param.getLabel().trim());
        tenant.setUrl(param.getUrl().trim());

        tenant.setCreatedBy(requesterPk);
        tenant.setUpdatedBy(requesterPk);
        tenant = tenantRepo.save(tenant);
        return tenant;
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "Tenant", allEntries = true),
            @CacheEvict(value = "TenantList", allEntries = true)
    })
    public void deleteTenant(Long tenantPk, User requester) {
        Long requesterPk = requester.getId();
        TenantEntity tenant = selectTenant(tenantPk);
        if (!tenant.isUseYn()) {
            throw new TenantException(TENANT_DELETED);
        }
        if (tenant.isDefault()) {
            throw new TenantException(TENANT_DEFAULT_CAN_NOT_DELETE);
        }
        tenant.setUseYn(false);
        tenant.setVisibleYn(false);
        tenant.setUpdatedBy(requesterPk);
        tenantRepo.save(tenant);
    }

    public void createTenantBootstrapSet(Tenant tenant, TenantRequests.CreateTenantRequest param, User requester) {
        Long tenantPk = tenant.getId();
        TeamRequests.UpsertTeamRequest teamParam = new TeamRequests.UpsertTeamRequest();
        teamParam.setTenantPk(tenantPk);
        teamParam.setTeamName("ADMIN TEAM");
        Team team = teamService.createTeam(teamParam, requester);
        Long teamPk = team.getId();

        TenantBootstrapRequests.CreateTenantRoleRequest roleParam = new TenantBootstrapRequests.CreateTenantRoleRequest();
        roleParam.setTenantPk(tenantPk);
        Role role = roleService.createRole(roleParam, requester);
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        UserRequests.UpsertUserRequest userParam = new UserRequests.UpsertUserRequest();
        userParam.setTenantPk(tenantPk);
        userParam.setTeamPk(teamPk);
        String label = tenant.getLabel();
        String userId = param.getUserId();
        String email = userId + "@" + label;
        String userName = param.getUserName();
        String password = param.getUserPwd();
        userParam.setUserId(userId);
        userParam.setUserEmail(email);
        userParam.setUserName(userName);
        userParam.setUserPwd(password);
        userService.createUser(userParam, roleList, requester);
    }

    @Override
    @Transactional
    public void bootstrap() {
/*        TenantEntity defaultTenant = selectDefatultTenant();
        if (defaultTenant != null) {
            return;
        }

        TenantEntity tenant = new TenantEntity();
        tenant.setDefaultYn(true);
        tenant.setUseYn(true);
        tenant.setVisibleYn(true);

        tenant.setTitle("default");
        tenant.setLabel("default");
        tenant.setUrl("http://localhost:8080");

        tenant.setCreatedBy(1L);
        tenant.setUpdatedBy(1L);
        tenant = tenantRepo.save(tenant);
        createTenantBootstrapSet(tenant, param, requester);*/
    }
}

