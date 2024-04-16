package xyz.needpainkiller.api.team.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import xyz.needpainkiller.api.team.model.TeamLevel;
import xyz.needpainkiller.lib.validation.NonSpecialCharacter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TeamRequests {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class UpsertTeamRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 5071775155208460067L;
        @JsonIgnore
        private Long tenantPk;
        @NotBlank
        @NonSpecialCharacter
        @Schema(description = "팀 이름", example = "인사팀", required = true)
        private String teamName;
        @NotNull
        @Schema(description = "팀 타입", example = "ROOT/NODE", required = true)
        private TeamLevel teamLevel = TeamLevel.ROOT;
        @Schema(description = "상위 팀 PK", example = "14 / null", required = true)
        private Long parentTeamPk = null;

        @NotNull
        @Min(0)
        @Schema(description = "순서", example = "1", required = true)
        private Integer order = 1;
    }


    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class ActiveTeamRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 207471853659049509L;
        @NotNull
        @Schema(description = "팀 PK 리스트", example = "[24,23,76]", required = true)
        private List<Long> teamPkList;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema
    public static class BulkTeamListRequest implements Serializable {
        @Serial
        private static final long serialVersionUID = 4190505360552688518L;

        @NotNull
        @Schema(description = "일괄 등록 팀 리스트", required = true)
        private List<BulkTeamRequest> teamRequestList;

        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        @Schema
        public static class BulkTeamRequest implements Serializable {
            @Serial
            private static final long serialVersionUID = 4418237205420557939L;
            @Schema(description = "팀 ID", example = "21", required = true)
            private Long id;
            @NotBlank
            @NonSpecialCharacter
            @Schema(description = "팀 이름", example = "인사팀", required = true)
            private String teamName;
            @NotNull
            @Schema(description = "팀 타입", example = "ROOT/NODE", required = true)
            private TeamLevel teamLevel = TeamLevel.ROOT;
            @Schema(description = "상위 팀 PK", example = "20 / null", required = true)
            private Long parentTeamPk = null;

            @JsonIgnore
            private Integer order = 1;
        }
    }

}
