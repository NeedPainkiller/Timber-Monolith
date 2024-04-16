package xyz.needpainkiller.base.tenant;

import xyz.needpainkiller.base.tenant.dto.TenantRequests;
import xyz.needpainkiller.base.tenant.model.Tenant;
import xyz.needpainkiller.base.user.model.User;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface TenantService<T extends Tenant> {
    static final Predicate<Tenant> predicateAvailableTenant = Tenant::isActive;
    static final Predicate<Tenant> predicatePublicTenant = Tenant::isPublic;
    static final Predicate<Tenant> predicateDefaultTenant = Tenant::isDefault;

    List<T> selectTenantList();

    List<T> selectPublicTenantList();

    Map<Long, T> selectTenantMap();

    List<Long> selectTenantPkList();

    T selectTenant(Long tenantPk);

    T selectDefatultTenant();

    T createTenant(TenantRequests.CreateTenantRequest param, User requester);

    T updateTenant(Long tenantPk, TenantRequests.UpdateTenantRequest param, User requester);

    void deleteTenant(Long tenantPk, User requester);
}
