package xyz.needpainkiller.api.tenant.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.tenant.model.Tenant;

import java.util.List;
import java.util.Optional;

public interface TenantRepo extends JpaRepository<Tenant, Long> {
    @Cacheable(value = "TenantList", key = "'findAll'")
    List<Tenant> findAll();

    @Override
    Optional<Tenant> findById(@NotNull Long teamPk);
}
