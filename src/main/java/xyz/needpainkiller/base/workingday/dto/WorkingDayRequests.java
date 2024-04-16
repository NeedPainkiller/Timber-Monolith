package xyz.needpainkiller.base.workingday.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class WorkingDayRequests {
    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class UpsertHoliday implements Serializable {
        @Serial
        private static final long serialVersionUID = 7625595440288959483L;
        @NotBlank
        @Schema(description = "제목", example = "12")
        private String title;
        @NotNull
        @Schema(description = "시작일", example = "1665488733201")
        private Timestamp start;
        @NotNull
        @Schema(description = "종료일", example = "1665488733201")
        private Timestamp end;
        @Schema(description = "커스텀 데이터", example = "{\"test\" : \"sample\"}", required = true)
        private Map<String, Serializable> data;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UpsertHoliday that = (UpsertHoliday) o;
            return Objects.equals(title, that.title) && Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(data, that.data);
        }

        @Override
        public int hashCode() {
            return Objects.hash(title, start, end, data);
        }
    }
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class BulkHolidayListRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 4190505360552688518L;

        @NotNull
        @Schema(description = "일괄 등록 휴일 리스트", required = true)
        private List<UpsertHoliday> HolidayRequestList;
    }
}
