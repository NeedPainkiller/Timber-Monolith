package xyz.needpainkiller.base.workingday.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@ToString
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkingDay implements Serializable {
    @Serial
    private static final long serialVersionUID = 8797243387986523426L;
    private final LocalDate date;
    private final Boolean isHoliday;
    private final Boolean isWeekend;
    private final Integer workDayOfYear;
    private final Integer workDayOfMonth;

    public WorkingDay(LocalDate date, Boolean isHoliday, Boolean isWeekend, Integer workDayOfYear, Integer workDayOfMonth) {
        this.date = date;
        this.isHoliday = isHoliday;
        this.isWeekend = isWeekend;
        this.workDayOfYear = workDayOfYear;
        this.workDayOfMonth = workDayOfMonth;
    }
}
