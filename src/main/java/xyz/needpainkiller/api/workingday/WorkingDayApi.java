package xyz.needpainkiller.api.workingday;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.needpainkiller.api.workingday.dto.WorkingDay;
import xyz.needpainkiller.api.workingday.dto.WorkingDayRequests;

import java.util.Map;

@Tag(name = "9999004. 영업일", description = "WORKINGDAY")
@RequestMapping(value = "/api/v1/workingday", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface WorkingDayApi {


    @Operation(description = "금일 영업일 조회")
    @GetMapping(value = "/today")
    ResponseEntity<WorkingDay> selectThisWorkingDay(HttpServletRequest request);

    @Operation(description = "휴일 리스트 조회")
    @GetMapping(value = "")
    ResponseEntity<Map<String, Object>> selectHolidayList(HttpServletRequest request);


    @PostMapping(value = "")
    @Operation(description = "휴일 등록")
    ResponseEntity<Map<String, Object>> createHoliday(@Valid @RequestBody WorkingDayRequests.UpsertHoliday param,
                                                      HttpServletRequest request);

    @PutMapping(value = "/{holidayPk}")
    @Operation(description = "휴일 수정")
    ResponseEntity<Map<String, Object>> updateHoliday(
            @Valid @NotBlank @Parameter(name = "holidayPk", example = "1", required = true) @PathVariable("holidayPk") Long holidayPk,
            @Valid @RequestBody WorkingDayRequests.UpsertHoliday param,
            HttpServletRequest request);

    @DeleteMapping(value = "/{holidayPk}")
    @Operation(description = "휴일 삭제")
    ResponseEntity<Map<String, Object>> deleteHoliday(
            @Valid @NotBlank @Parameter(name = "holidayPk", example = "1", required = true) @PathVariable("holidayPk") Long holidayPk,
            HttpServletRequest request);


    @Operation(description = "휴일 다운로드")
    @GetMapping(value = "/download")
    void downloadHolidayList(HttpServletRequest request, HttpServletResponse response);

    @PostMapping(value = "/upload")
    @Operation(description = "휴일 일괄 등록")
    ResponseEntity<Map<String, Object>> uploadHolidayBulk(@Valid @RequestBody WorkingDayRequests.BulkHolidayListRequest param,
                                                          HttpServletRequest request);

}