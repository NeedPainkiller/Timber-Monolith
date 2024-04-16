package xyz.needpainkiller.api.team.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.team.model.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRepo extends JpaRepository<Team, Long> {
    //    @Cacheable(value = "TeamList", key = "'findAll'")
    List<Team> findAll();

    @Override
//    @Cacheable(value = "Team", key = "'findById-' + #p0", condition = "#p0!=null")
    Optional<Team> findById(@NotNull Long teamPk);

    List<Team> findByParentTeamPk(@NotNull Long parentTeamPk);
}
