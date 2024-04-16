package xyz.needpainkiller.api.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.user.model.UserRoleMapEntity;
import xyz.needpainkiller.api.user.model.UserRoleMapId;

import java.util.List;

public interface UserRoleMapRepo extends JpaRepository<UserRoleMapEntity, UserRoleMapId> {
    List<UserRoleMapEntity> findByUserPk(Long userPk);

    List<UserRoleMapEntity> findByUserPkIn(List<Long> userPkList);

    List<UserRoleMapEntity> findByRolePk(Long rolePk);

    List<UserRoleMapEntity> findByRolePkIn(List<Long> rolePkList);
}