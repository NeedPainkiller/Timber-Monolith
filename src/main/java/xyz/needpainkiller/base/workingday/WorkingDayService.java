package xyz.needpainkiller.base.workingday;

import xyz.needpainkiller.base.user.model.User;
import xyz.needpainkiller.base.workingday.dto.WorkingDay;
import xyz.needpainkiller.base.workingday.dto.WorkingDayRequests;
import xyz.needpainkiller.base.workingday.error.WorkingDayErrorCode;
import xyz.needpainkiller.base.workingday.error.WorkingDayException;
import xyz.needpainkiller.base.workingday.model.Holiday;
import xyz.needpainkiller.helper.DateRange;
import xyz.needpainkiller.helper.TimeHelper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface WorkingDayService<T extends Holiday> {
    default List<LocalDate> toLocalDateList(T holiday) {
        LocalDate startDate = TimeHelper.fromTimestampToLocalDate(holiday.getStart());
        LocalDate endDate = TimeHelper.fromTimestampToLocalDate(holiday.getEnd());
        return new DateRange(startDate, endDate).toList();
    }

    default List<LocalDate> toLocalDateList(List<T> holidayList) {
        return holidayList.stream()
                .map(this::toLocalDateList)
                .flatMap(Collection::stream)
                .distinct().toList();
    }

    static boolean scope(LocalDate localDate, LocalDate from, LocalDate to) {
        return localDate.isAfter(from) && localDate.isBefore(to);
    }

    List<LocalDate> monthlyWorkingDate(Long tenantPk, LocalDate date);

    LocalDate previousWorkingDate(Long tenantPk, LocalDate date);

    LocalDate nextWorkingDate(Long tenantPk, LocalDate date);

    LocalDate monthFirstWorkingDate(Long tenantPk, LocalDate date);

    LocalDate monthLastWorkingDate(Long tenantPk, LocalDate date);

    WorkingDay workingDayOfDate(Long tenantPk, LocalDate date);

    WorkingDay workingDayOfDate(Long tenantPk, LocalDate date,
                                Integer relativeDayOffset, boolean offsetIncludeHoliday);

    List<T> selectHolidayList(Long tenantPk);

    List<LocalDate> selectHolidayLocalDateList(Long tenantPk);

    T createHoliday(WorkingDayRequests.UpsertHoliday param, User requester, Long tenantPk);

    T updateHoliday(Long holidayPk, WorkingDayRequests.UpsertHoliday param, User requester);

    void deleteHoliday(Long holidayPk, User requester);

    default void checkHolidayValidation(WorkingDayRequests.UpsertHoliday param) {
        Timestamp start = param.getStart();
        Timestamp end = param.getEnd();
        if (end.before(start)) {
            throw new WorkingDayException(WorkingDayErrorCode.ORG_WORKING_DAY_WRONG_DATE_RANGE);
        }
    }
}
