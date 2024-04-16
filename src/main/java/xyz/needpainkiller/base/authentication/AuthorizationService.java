package xyz.needpainkiller.base.authentication;

import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.base.authentication.model.Api;
import xyz.needpainkiller.base.authentication.model.Division;
import xyz.needpainkiller.base.authentication.model.Menu;
import xyz.needpainkiller.base.user.model.Role;
import xyz.needpainkiller.common.model.HttpMethod;

import java.util.List;

public interface AuthorizationService<D extends Division, M extends  Menu, A extends Api, R extends  Role> {
    A selectApi(Long apiPk);

    A selectApiByRequestURI(String requestURI, HttpMethod httpMethod);

   List<A> selectAvailableApiList();

    List<A> selectVisibleApiList();

    List<A> selectPrimaryApiList();

    List<A> selectPublicApiList();

    List<A> selectNonPublicApiList();

    List<A> selectApiListByRole(R role);

    List<A> selectApiListByRolePk(Long rolePk);

    List<A> selectApiListByRoleList(List<R> roleList);

    List<A> selectApiListByRolePkList(List<Long> rolePkList);

    List<R> selectRoleListByApiPk(Long apiPk);

    List<R> selectRoleListByApiPkList(List<Long> apiPkList);

    List<A> selectApiListByMenuPk(Long menuPk);

    List<A> selectApiListByMenuPkList(List<Long> menuPkList);

    M selectMenuByApi(A api);

    List<M> selectAvailableMenuList();

    List<M> selectAvailableMenuListByApiList(List<A> apiList);

    @Transactional()
    void upsertApiRole(R role, List<Long> apiPkList);

    default List<Long> cleanUpApiPkList(List<Long> apiPkList) {
        List<A> apiList = selectAvailableApiList().stream().filter(api -> apiPkList.contains(api.getId())).toList();
        List<A> primaryApiList = selectPrimaryApiList().stream().filter(api -> apiPkList.contains(api.getId())).toList();
        List<M> menuList = selectAvailableMenuListByApiList(primaryApiList);
        List<Long> menuPkList = menuList.stream().map(Menu::getId).toList();
        List<Long> apiPkListByMenu = selectApiListByMenuPkList(menuPkList).stream().map(Api::getId).toList();
        return apiList.stream().map(Api::getId).filter(apiPkListByMenu::contains).toList();
    }

    List<D> selectAvailableDivision();

    List<D> selectDivisionByRole(R role);

    List<D> selectDivisionByRolePk(Long rolePk);

    List<D> selectDivisionByRoleList(List<R> roleList);

    List<D> selectDivisionByRolePkList(List<Long> rolePkList);

    List<D> selectDivisionByApiList(List<A> apiList);
}
