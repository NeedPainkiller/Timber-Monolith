package xyz.needpainkiller.api.authentication.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.Menu;

import java.util.List;


public interface MenuRepo extends JpaRepository<Menu, Long> {

    @Cacheable(value = "MenuList", key = "'findAll'")
    List<Menu> findAll();

    @Cacheable(value = "MenuList", key = "'findAllByIdIn' + #p0.hashCode()", condition = "#p0!=null")
    List<Menu> findAllByIdIn(List<Long> idList);
}