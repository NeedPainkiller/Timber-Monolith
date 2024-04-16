package xyz.needpainkiller.api.team.dao;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.team.model.TeamEntity;

import java.util.List;
import java.util.Optional;

public interface TeamRepo extends JpaRepository<TeamEntity, Long> {
    //    @Cacheable(value = "TeamList", key = "'findAll'")
    List<TeamEntity> findAll();

    @Override
//    @Cacheable(value = "Team", key = "'findById-' + #p0", condition = "#p0!=null")
    Optional<TeamEntity> findById(@NotNull Long teamPk);

    List<TeamEntity> findByParentTeamPk(@NotNull Long parentTeamPk);
}
