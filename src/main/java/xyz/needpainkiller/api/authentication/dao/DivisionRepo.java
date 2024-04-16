package xyz.needpainkiller.api.authentication.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.Division;

import java.util.List;


public interface DivisionRepo extends JpaRepository<Division, Long> {

    @Cacheable(value = "DivisionList", key = "'findAll'")
    List<Division> findAll();

    @Cacheable(value = "DivisionList", key = "'findAllByIdIn-' + #p0.hashCode()", condition = "#p0!=null")
    List<Division> findAllByIdIn(List<Long> idList);
}
