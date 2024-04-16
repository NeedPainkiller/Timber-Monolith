package xyz.needpainkiller.api.workingday;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.needpainkiller.api.workingday.dao.HolidayRepo;
import xyz.needpainkiller.api.workingday.model.HolidayEntity;
import xyz.needpainkiller.base.tenant.error.TenantException;
import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.base.workingday.WorkingDayService;
import xyz.needpainkiller.base.workingday.dto.WorkingDay;
import xyz.needpainkiller.base.workingday.dto.WorkingDayRequests;
import xyz.needpainkiller.base.workingday.error.WorkingDayErrorCode;
import xyz.needpainkiller.base.workingday.error.WorkingDayException;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

import static java.time.temporal.TemporalAdjusters.*;
import static xyz.needpainkiller.base.tenant.error.TenantErrorCode.TENANT_CONFLICT;

@Slf4j
@Service
public class WorkingDayServiceImpl implements WorkingDayService<HolidayEntity> {
    private static final Predicate<LocalDate> IS_WEEKEND = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    private final HolidayRepo holidayRepo;

    @Autowired
    public WorkingDayServiceImpl(HolidayRepo holidayRepo) {
        this.holidayRepo = holidayRepo;
    }

    public List<LocalDate> monthlyWorkingDate(Long tenantPk, LocalDate date) {
        LocalDate firstDay = date.with(firstDayOfMonth());
        LocalDate lastDay = date.with(lastDayOfMonth()).plusDays(1);

        List<HolidayEntity> holidayList = holidayRepo.findAllByTenantPk(tenantPk);
        List<LocalDate> holidayDateList = toLocalDateList(holidayList);

        return firstDay.datesUntil(lastDay)
                .filter(WorkingDayServiceImpl.IS_WEEKEND.negate())
                .filter(localDate -> !holidayDateList.contains(localDate))
                .toList();
    }

    @Override
    public LocalDate previousWorkingDate(Long tenantPk, LocalDate date) {
        LocalDate from = date.minusYears(1).with(firstDayOfYear());
        List<HolidayEntity> holidayList = holidayRepo.findAllByTenantPk(tenantPk);
        List<LocalDate> holidayDateList = toLocalDateList(holidayList);


        return from.datesUntil(date)
                .filter(IS_WEEKEND.negate())
                .filter(localDate -> !holidayDateList.contains(localDate))
                .reduce((first, second) -> second).orElse(null);
    }

    @Override
    public LocalDate nextWorkingDate(Long tenantPk, LocalDate date) {
        LocalDate tomorrow = date.plusDays(1);
        LocalDate to = tomorrow.plusYears(1).with(lastDayOfYear());
        List<HolidayEntity> holidayList = holidayRepo.findAllByTenantPk(tenantPk);
        List<LocalDate> holidayDateList = toLocalDateList(holidayList);


        return tomorrow.datesUntil(to)
                .filter(IS_WEEKEND.negate())
                .filter(localDate -> !holidayDateList.contains(localDate))
                .reduce((first, second) -> first).orElse(null);
    }

    @Override
    public LocalDate monthFirstWorkingDate(Long tenantPk, LocalDate date) {
        List<LocalDate> workingLocalDates = monthlyWorkingDate(tenantPk, date);
        Optional<LocalDate> firstWorkLocalDateOpt = workingLocalDates.stream().findFirst();
        return firstWorkLocalDateOpt.orElse(null);
    }

    @Override
    public LocalDate monthLastWorkingDate(Long tenantPk, LocalDate date) {
        List<LocalDate> workingLocalDates = monthlyWorkingDate(tenantPk, date);
        Optional<LocalDate> lastWorkLocalDateOpt = workingLocalDates.stream().reduce((first, second) -> second);
        return lastWorkLocalDateOpt.orElse(null);
    }

    /**
     * 영업일 정보 조회
     *
     * @param tenantPk 테넌트 PK
     * @param date     기준일자
     */
    @Override
    public WorkingDay workingDayOfDate(Long tenantPk, LocalDate date) {
        return workingDayOfDate(tenantPk, date, 0, true);
    }

    /**
     * 영업일 정보 조회
     *
     * @param tenantPk             테넌트 PK
     * @param date                 기준일자
     * @param relativeDayOffset    기준일자 기점 일자 추가 차감 오프셋
     * @param offsetIncludeHoliday 오프셋에 휴일 포함 여부
     */
    @Override
    public WorkingDay workingDayOfDate(Long tenantPk, LocalDate date,
                                       Integer relativeDayOffset, boolean offsetIncludeHoliday) {

        boolean isHoliday;
        boolean isWeekend;
        int workDayOfYear;
        int workDayOfMonth;

        List<HolidayEntity> holidayList = holidayRepo.findAllByTenantPk(tenantPk);
        List<LocalDate> holidayDateList = toLocalDateList(holidayList);

        if (relativeDayOffset == null || relativeDayOffset == 0) {
            isHoliday = holidayDateList.contains(date);
            isWeekend = IS_WEEKEND.test(date);
        } else {
            int addition = relativeDayOffset > 0 ? 1 : -1;
            int currentOffset = 0;
            while (currentOffset != relativeDayOffset) {
                date = date.plusDays(addition);
                if (!offsetIncludeHoliday && (IS_WEEKEND.test(date) || holidayDateList.contains(date))) {
                    continue;
                }
                currentOffset = currentOffset + addition;
            }
            isHoliday = holidayDateList.contains(date);
            isWeekend = IS_WEEKEND.test(date);
        }

        LocalDate nextDay = date.plusDays(1);

        LocalDate firstDayOfYear = date.with(firstDayOfYear());
        List<LocalDate> workDayOfYearList = firstDayOfYear.datesUntil(nextDay)
                .filter(IS_WEEKEND.or(holidayDateList::contains).negate())
                .toList();
        workDayOfYear = workDayOfYearList.size();


        LocalDate firstDayOfMonth = date.with(firstDayOfMonth());
        List<LocalDate> workDayOfMonthList = firstDayOfMonth.datesUntil(nextDay)
                .filter(IS_WEEKEND.or(holidayDateList::contains).negate())
                .toList();
        workDayOfMonth = workDayOfMonthList.size();

        return new WorkingDay(date, isHoliday, isWeekend, workDayOfYear, workDayOfMonth);
    }

    @Override
    public List<HolidayEntity> selectHolidayList(Long tenantPk) {
        return holidayRepo.findAllByTenantPk(tenantPk);
    }

    @Override
    public List<LocalDate> selectHolidayLocalDateList(Long tenantPk) {
        List<HolidayEntity> holidayList = selectHolidayList(tenantPk);
        return toLocalDateList(holidayList);
    }

    @Override
    public HolidayEntity createHoliday(WorkingDayRequests.UpsertHoliday param, User requester, Long tenantPk) {
        checkHolidayValidation(param);
        Timestamp start = param.getStart();
        Timestamp end = param.getEnd();

        HolidayEntity holiday = new HolidayEntity();
        holiday.setTenantPk(tenantPk);
        holiday.setUuid(UUID.randomUUID().toString());
        holiday.setTitle(param.getTitle().trim());
        holiday.setStart(start);
        holiday.setEnd(end);
        holiday.setData(param.getData());
        return holidayRepo.save(holiday);
    }

    @Override
    public HolidayEntity updateHoliday(Long holidayPk, WorkingDayRequests.UpsertHoliday param, User requester) {
        checkHolidayValidation(param);
        Timestamp start = param.getStart();
        Timestamp end = param.getEnd();

        Long tenantPk = requester.getTenantPk();

        Optional<HolidayEntity> optionalHoliday = holidayRepo.findById(holidayPk);

        if (optionalHoliday.isEmpty()) {
            throw new WorkingDayException(WorkingDayErrorCode.ORG_WORKING_DAY_NOT_EXIST);
        }
        HolidayEntity holiday = optionalHoliday.get();

        if (!holiday.getTenantPk().equals(tenantPk)) {
            throw new TenantException(TENANT_CONFLICT);
        }

        holiday.setTenantPk(tenantPk);
        holiday.setTitle(param.getTitle().trim());
        holiday.setStart(start);
        holiday.setEnd(end);
        holiday.setData(param.getData());
        return holidayRepo.save(holiday);
    }

    @Override
    public void deleteHoliday(Long holidayPk, User requester) {
        Optional<HolidayEntity> optionalHoliday = holidayRepo.findById(holidayPk);
        if (optionalHoliday.isEmpty()) {
            throw new WorkingDayException(WorkingDayErrorCode.ORG_WORKING_DAY_NOT_EXIST);
        }
        Long tenantPk = requester.getTenantPk();
        HolidayEntity holiday = optionalHoliday.get();
        if (!holiday.getTenantPk().equals(tenantPk)) {
            throw new TenantException(TENANT_CONFLICT);
        }
        holidayRepo.delete(holiday);
    }

}
