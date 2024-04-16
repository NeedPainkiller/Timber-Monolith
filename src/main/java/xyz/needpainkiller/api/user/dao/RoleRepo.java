package xyz.needpainkiller.api.user.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import xyz.needpainkiller.api.user.model.RoleEntity;

import java.util.List;

public interface RoleRepo extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {

    @Cacheable(value = "RoleList", key = "'findAll'")
    List<RoleEntity> findAll();

    @Cacheable(value = "RoleList", key = "'findByIdIn-' + #p0.hashCode()", condition = "#p0!=null")
    List<RoleEntity> findByIdIn(List<Long> idList);
}