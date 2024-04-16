package xyz.needpainkiller.api.team;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.needpainkiller.base.team.dto.TeamRequests;

import java.util.Map;

@Tag(name = "9999003. 관리자 > 부서 관리", description = "TEAM")
@RequestMapping(value = "/api/v1/team", produces = {MediaType.APPLICATION_JSON_VALUE})
public interface TeamApi {


    @Operation(description = "부서 전체 리스트 조회")
    @PostMapping(value = "/list")
    ResponseEntity<Map<String, Object>> selectTeamList(HttpServletRequest request);

    @Operation(description = "부서 조회")
    @GetMapping("/{teamPk}")
    ResponseEntity<Map<String, Object>> selectTeam(@Parameter(name = "teamPk", example = "4", required = true)
                                                   @PathVariable("teamPk") Long teamPk, HttpServletRequest request);

    @PostMapping(value = "")
    @Operation(description = "부서 등록")
    ResponseEntity<Map<String, Object>> createTeam(@Valid @RequestBody TeamRequests.UpsertTeamRequest param,
                                                   HttpServletRequest request);

    @PutMapping(value = "/{teamPk}")
    @Operation(description = "부서 수정")
    ResponseEntity<Map<String, Object>> updateTeam(@NotBlank @PathVariable("teamPk") Long teamPk,
                                                   @Valid @RequestBody TeamRequests.UpsertTeamRequest param,
                                                   HttpServletRequest request);

    @DeleteMapping(value = "/{teamPk}")
    @Operation(description = "부서 삭제")
    ResponseEntity<Map<String, Object>> deleteTeam(@NotBlank @PathVariable("teamPk") Long teamPk,
                                                   HttpServletRequest request);

    @PostMapping(value = "/bulk")
    @Operation(description = "부서 일괄 등록")
    ResponseEntity<Map<String, Object>> createTeamBulk(@Valid @RequestBody TeamRequests.BulkTeamListRequest param,
                                                       HttpServletRequest request);

}