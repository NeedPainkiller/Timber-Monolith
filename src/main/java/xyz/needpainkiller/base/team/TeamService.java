package xyz.needpainkiller.base.team;

import org.springframework.transaction.annotation.Transactional;
import xyz.needpainkiller.base.team.dto.TeamRequests;
import xyz.needpainkiller.base.team.model.Team;
import xyz.needpainkiller.base.user.model.User;

import java.util.List;
import java.util.Map;

public interface TeamService<T extends Team> {
    List<T> selectTeamList(Long tenantPk);

    List<T> selectTeamList();

    Map<Long, T> selectTeamMap();

    T selectTeam(Long teamPk);

    List<T> selectFlattenTeam(Long teamPk);

    @Transactional
    T createTeam(TeamRequests.UpsertTeamRequest param, User requester);

    @Transactional
    T updateTeam(Long teamPk, TeamRequests.UpsertTeamRequest param, User requester);

    @Transactional
    void deleteTeam(Long teamPk, User requester, Long tenantPk);

    void validateParentTeam(TeamRequests.UpsertTeamRequest param);

    boolean hasChildrenTeam(Long teamPk);

    void updateAllTeamPath();

    @Transactional
     List<Team> createTeamBulk(TeamRequests.BulkTeamListRequest param, User requester);
}
