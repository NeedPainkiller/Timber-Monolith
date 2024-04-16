package xyz.needpainkiller.api.workingday.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.needpainkiller.api.workingday.model.Holiday;
import xyz.needpainkiller.helper.TimeHelper;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayCsv implements Serializable {
    @Serial
    private static final long serialVersionUID = 682331965630019511L;
    @JsonProperty(value = "제목", index = 1)
    private String title;
    @JsonProperty(value = "시작일", index = 2)
    private String start;
    @JsonProperty(value = "종료일", index = 3)
    private String end;

    public HolidayCsv(Holiday holiday) {

        this.title = holiday.getTitle();

        Timestamp startTimestamp = holiday.getStart();
        this.start = TimeHelper.DEF_DATE_FORMAT.format(startTimestamp.toLocalDateTime());


        Timestamp endTimestamp = holiday.getEnd();
        this.end = TimeHelper.DEF_DATE_FORMAT.format(endTimestamp.toLocalDateTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HolidayCsv that = (HolidayCsv) o;
        return Objects.equals(title, that.title) && Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, start, end);
    }
}
