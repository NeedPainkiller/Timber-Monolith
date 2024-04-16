package xyz.needpainkiller.api.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.AntPathMatcher;
import xyz.needpainkiller.api.authentication.dao.ApiRepo;
import xyz.needpainkiller.api.authentication.dao.ApiRoleMapRepo;
import xyz.needpainkiller.api.authentication.dao.DivisionRepo;
import xyz.needpainkiller.api.authentication.dao.MenuRepo;
import xyz.needpainkiller.api.authentication.model.ApiEntity;
import xyz.needpainkiller.api.authentication.model.ApiRoleMapEntity;
import xyz.needpainkiller.api.authentication.model.DivisionEntity;
import xyz.needpainkiller.api.authentication.model.MenuEntity;
import xyz.needpainkiller.api.user.dao.RoleRepo;
import xyz.needpainkiller.api.user.model.RoleEntity;
import xyz.needpainkiller.base.authentication.AuthorizationService;
import xyz.needpainkiller.base.authentication.error.ApiException;
import xyz.needpainkiller.base.authentication.model.Api;
import xyz.needpainkiller.base.authentication.model.ApiRoleMap;
import xyz.needpainkiller.base.authentication.model.Division;
import xyz.needpainkiller.base.authentication.model.Menu;
import xyz.needpainkiller.base.user.error.RoleException;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.common.model.HttpMethod;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.*;

@Slf4j
@Service
public class AuthorizationServiceImpl implements AuthorizationService<DivisionEntity, MenuEntity, ApiEntity, RoleEntity> {
    private static final AntPathMatcher apiAntPathMatcher = new AntPathMatcher();
    private static final Comparator<Division> divisionSort = Comparator.comparingInt(Division::getOrder);
    private static final Comparator<Menu> menuSort = Comparator.comparingInt(Menu::getOrder);
    private static final Comparator<Api> apiSort = Comparator.comparingLong(Api::getId);
    @Autowired
    private DivisionRepo divisionRepo;
    @Autowired
    private MenuRepo menuRepo;
    @Autowired
    private ApiRepo apiRepo;
    @Autowired
    private ApiRoleMapRepo apiRoleMapRepo;
    @Autowired
    private RoleRepo roleRepo;

    @Override
    @Cacheable(value = "Api", key = "'selectApi-' + #apiPk")
    public ApiEntity selectApi(Long apiPk) {
        return apiRepo.findById(apiPk).orElseThrow(() -> new ApiException(API_NOT_FOUND));
    }

    @Override
    public ApiEntity selectApiByRequestURI(String requestURI, HttpMethod httpMethod) {
        return selectAvailableApiList().stream()
                .filter(api -> apiAntPathMatcher.match(api.getUrl(), requestURI))
                .filter(api -> api.getHttpMethod().equals(httpMethod))
                .findFirst().orElseThrow(() -> new ApiException(API_NOT_FOUND));
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectAvailableApiList'")
    public List<ApiEntity> selectAvailableApiList() {
        return apiRepo.findAll().stream().filter(Api::isAvailableApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectVisibleApiList'")
    public List<ApiEntity> selectVisibleApiList() {
        return apiRepo.findAll().stream().filter(Api::isVisibleApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectPrimaryApiList'")
    public List<ApiEntity> selectPrimaryApiList() {
        return apiRepo.findAll().stream().filter(Api::isPrimaryApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectPublicApiList'")
    public List<ApiEntity> selectPublicApiList() {
        return apiRepo.findAll().stream().filter(Api::isPublicApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectNonPublicApiList'")
    public List<ApiEntity> selectNonPublicApiList() {
        return apiRepo.findAll().stream().filter(Api::isNonPublicApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectApiListByRole-' + #role.getId()")
    public List<ApiEntity> selectApiListByRole(RoleEntity role) {
        return selectApiListByRolePk(role.getId());
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectApiListByRolePk-' + #rolePk")
    public List<ApiEntity> selectApiListByRolePk(Long rolePk) {
        List<Long> apiPkList = apiRoleMapRepo.findByRolePk(rolePk).stream().map(ApiRoleMap::getApiPk).distinct().toList();
        return apiRepo.findByIdIn(apiPkList).stream().filter(Api::isAvailableApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectApiListByRoleList-' + #roleList.hashCode()")
    public List<ApiEntity> selectApiListByRoleList(List<RoleEntity> roleList) {
        List<Long> rolePkList = roleList.stream().map(Role::getId).toList();
        return selectApiListByRolePkList(rolePkList);
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectApiListByRolePkList-' + #rolePkList.hashCode()")
    public List<ApiEntity> selectApiListByRolePkList(List<Long> rolePkList) {
        rolePkList = rolePkList.stream().distinct().toList();
        List<Long> apiPkList = apiRoleMapRepo.findByRolePkIn(rolePkList).stream().map(ApiRoleMap::getApiPk).distinct().toList();
        return apiRepo.findByIdIn(apiPkList).stream().filter(Api::isAvailableApi).sorted(apiSort).toList();
    }

    @Override
    @Cacheable(value = "ApiRoleList", key = "'selectRoleListByApiPk-' + #apiPk")
    public List<RoleEntity> selectRoleListByApiPk(Long apiPk) {
        List<Long> rolePkList = apiRoleMapRepo.findByApiPk(apiPk).stream().map(ApiRoleMap::getRolePk).distinct().toList();
        return roleRepo.findByIdIn(rolePkList).stream().filter(Role::isAvailable).toList();
    }

    @Override
    @Cacheable(value = "ApiRoleList", key = "'selectRoleListByApiPkList-' + #apiPkList.hashCode()")
    public List<RoleEntity> selectRoleListByApiPkList(List<Long> apiPkList) {
        apiPkList = apiPkList.stream().distinct().toList();
        List<Long> rolePkList = apiRoleMapRepo.findByApiPkIn(apiPkList).stream().map(ApiRoleMap::getRolePk).distinct().toList();
        return roleRepo.findByIdIn(rolePkList).stream().filter(Role::isAvailable).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectApiListByMenuPk-' + #menuPk")
    public List<ApiEntity> selectApiListByMenuPk(Long menuPk) {
        return apiRepo.findByMenuPk(menuPk).stream().filter(Api::isAvailableApi).toList();
    }

    @Override
    @Cacheable(value = "ApiList", key = "'selectApiListByMenuPkList-' + #menuPkList.hashCode()")
    public List<ApiEntity> selectApiListByMenuPkList(List<Long> menuPkList) {
        menuPkList = menuPkList.stream().distinct().toList();
        return apiRepo.findByMenuPkIn(menuPkList).stream().filter(Api::isAvailableApi).toList();
    }

    //    @Cacheable(value = "Menu", key = "#api.getId()")
    @Override
    public MenuEntity selectMenuByApi(ApiEntity api) {
        return menuRepo.findById(api.getMenuPk()).orElseThrow(() -> new ApiException(MENU_NOT_FOUND));
    }

    @Override
    @Cacheable(value = "MenuList", key = "'selectAvailableMenuList'")
    public List<MenuEntity> selectAvailableMenuList() {
        return menuRepo.findAll().stream().filter(Menu::isAvailableMenu).sorted(menuSort).toList();
    }

    @Override
    @Cacheable(value = "MenuList", key = "'selectAvailableMenuListByApiList-' + #apiList.hashCode()")
    public List<MenuEntity> selectAvailableMenuListByApiList(List<ApiEntity> apiList) {
        List<Long> apiMenuPkList = apiList.stream().filter(Api::isPrimaryApi).map(Api::getMenuPk).distinct().toList();
        return menuRepo.findAllByIdIn(apiMenuPkList).stream().filter(Menu::isAvailableMenu).sorted(menuSort).toList();
    }

    @Override
    @Modifying(flushAutomatically = true)
    @Transactional()
    @Caching(evict = {
            @CacheEvict(value = "Api", allEntries = true),
            @CacheEvict(value = "ApiList", allEntries = true),
            @CacheEvict(value = "ApiRoleList", allEntries = true),
            @CacheEvict(value = "Menu", allEntries = true),
            @CacheEvict(value = "MenuList", allEntries = true),
            @CacheEvict(value = "DivisionList", allEntries = true)
    })
    public void upsertApiRole(RoleEntity role, List<Long> apiPkList) {
        if (!role.isEditable()) {
            throw new RoleException(ROLE_CAN_NOT_EDITABLE);
        }
        Long rolePk = role.getId();
        if (apiPkList.isEmpty()) {
            throw new ApiException(API_REQUEST_EMPTY);
        }
        List<Long> filteredApiPkList = cleanUpApiPkList(apiPkList);

        List<ApiRoleMapEntity> deleteApiRoleMapList = apiRoleMapRepo.findByRolePk(rolePk);
        deleteApiRoleMapList.forEach(apiRoleMap -> {
            apiRoleMapRepo.delete(apiRoleMap);
        });

        filteredApiPkList.forEach(apiPk -> {
            ApiRoleMapEntity apiRoleMap = new ApiRoleMapEntity();
            apiRoleMap.setApiPk(apiPk);
            apiRoleMap.setRolePk(rolePk);
            apiRoleMapRepo.save(apiRoleMap);
        });
    }


    @Override
    @Cacheable(value = "DivisionList", key = "'selectAvailableDivision'")
    public List<DivisionEntity> selectAvailableDivision() {
        List<ApiEntity> apiList = selectAvailableApiList();
        return selectDivisionByApiList(apiList);
    }

    @Override
    @Cacheable(value = "DivisionList", key = "'selectDivisionByRole-' + #role.getId()")
    public List<DivisionEntity> selectDivisionByRole(RoleEntity role) {
        List<ApiEntity> apiList = selectApiListByRolePk(role.getId());
        return selectDivisionByApiList(apiList);
    }

    @Override
    @Cacheable(value = "DivisionList", key = "'selectDivisionByRolePk-' + #rolePk")
    public List<DivisionEntity> selectDivisionByRolePk(Long rolePk) {
        List<ApiEntity> apiList = selectApiListByRolePk(rolePk);
        return selectDivisionByApiList(apiList);
    }

    @Override
    @Cacheable(value = "DivisionList", key = "'selectDivisionByRoleList-' + #roleList.hashCode()")
    public List<DivisionEntity> selectDivisionByRoleList(List<RoleEntity> roleList) {
        List<Long> rolePkList = roleList.stream().map(Role::getId).toList();
        List<ApiEntity> apiList = selectApiListByRolePkList(rolePkList);
        return selectDivisionByApiList(apiList);
    }

    @Override
    @Cacheable(value = "DivisionList", key = "'selectDivisionByRolePkList-' + #rolePkList.hashCode()")
    public List<DivisionEntity> selectDivisionByRolePkList(List<Long> rolePkList) {
        List<ApiEntity> apiList = selectApiListByRolePkList(rolePkList);
        return selectDivisionByApiList(apiList);
    }

    @Override
    @Cacheable(value = "DivisionList", key = "'selectDivisionByApiList-' + #apiList.hashCode()")
    public List<DivisionEntity> selectDivisionByApiList(List<ApiEntity> apiList) {
        Map<Long, List<Api>> menuApiGroup = apiList.stream().collect(Collectors.groupingBy(Api::getMenuPk));
        List<Long> menuPkList = apiList.stream().map(Api::getMenuPk).distinct().toList();
        List<MenuEntity> menuList = menuRepo.findAllByIdIn(menuPkList).stream().filter(Menu::isAvailableMenu)
                .map(menu -> menu.setApiList(menuApiGroup.get(menu.getId())))
                .sorted(menuSort).toList();
        Map<Long, List<Menu>> divisionMenuGroup = menuList.stream().collect(Collectors.groupingBy(Menu::getDivisionPk));
        List<Long> divisionPkList = menuList.stream().map(Menu::getDivisionPk).distinct().toList();
        return divisionRepo.findAllByIdIn(divisionPkList).stream().filter(Division::isAvailableDivision)
                .map(division -> division.setMenuList(divisionMenuGroup.get(division.getId())))
                .sorted(divisionSort).toList();
    }

}