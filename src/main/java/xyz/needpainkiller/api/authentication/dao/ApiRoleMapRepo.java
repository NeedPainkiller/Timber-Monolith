package xyz.needpainkiller.api.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.ApiRoleMapEntity;
import xyz.needpainkiller.api.authentication.model.ApiRoleMapId;

import java.util.List;


public interface ApiRoleMapRepo extends JpaRepository<ApiRoleMapEntity, ApiRoleMapId> {
    List<ApiRoleMapEntity> findByRolePk(Long rolePk);

    List<ApiRoleMapEntity> findByRolePkIn(List<Long> rolePkList);

    List<ApiRoleMapEntity> findByApiPk(Long apiPk);

    List<ApiRoleMapEntity> findByApiPkIn(List<Long> apiPkList);
}