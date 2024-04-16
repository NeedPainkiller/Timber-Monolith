package xyz.needpainkiller.api.authentication.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.ApiRoleMap;
import xyz.needpainkiller.api.authentication.model.ApiRoleMapId;

import java.util.List;


public interface ApiRoleMapRepo extends JpaRepository<ApiRoleMap, ApiRoleMapId> {
    List<ApiRoleMap> findByRolePk(Long rolePk);

    List<ApiRoleMap> findByRolePkIn(List<Long> rolePkList);

    List<ApiRoleMap> findByApiPk(Long apiPk);

    List<ApiRoleMap> findByApiPkIn(List<Long> apiPkList);
}