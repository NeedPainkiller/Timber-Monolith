package xyz.needpainkiller.api.authentication.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.DivisionEntity;

import java.util.List;


public interface DivisionRepo extends JpaRepository<DivisionEntity, Long> {

    @Cacheable(value = "DivisionList", key = "'findAll'")
    List<DivisionEntity> findAll();

    @Cacheable(value = "DivisionList", key = "'findAllByIdIn-' + #p0.hashCode()", condition = "#p0!=null")
    List<DivisionEntity> findAllByIdIn(List<Long> idList);
}
