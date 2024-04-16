package xyz.needpainkiller.api.workingday.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.workingday.model.HolidayEntity;

import java.util.List;
import java.util.Optional;

public interface HolidayRepo extends JpaRepository<HolidayEntity, Long> {
    @Override
    Optional<HolidayEntity> findById(@NotNull Long holidayPk);

    Optional<HolidayEntity> findByUuid(String uuid);

    @Override
    List<HolidayEntity> findAll();

    List<HolidayEntity> findAllByTenantPk(Long tenantPk);
}
