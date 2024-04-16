package xyz.needpainkiller.api.workingday.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.workingday.model.Holiday;

import java.util.List;
import java.util.Optional;

public interface HolidayRepo extends JpaRepository<Holiday, Long> {
    @Override
    Optional<Holiday> findById(@NotNull Long holidayPk);

    Optional<Holiday> findByUuid(String uuid);

    @Override
    List<Holiday> findAll();

    List<Holiday> findAllByTenantPk(Long tenantPk);
}
