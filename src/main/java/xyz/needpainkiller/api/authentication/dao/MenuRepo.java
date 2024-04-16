package xyz.needpainkiller.api.authentication.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.MenuEntity;

import java.util.List;


public interface MenuRepo extends JpaRepository<MenuEntity, Long> {

    @Cacheable(value = "MenuList", key = "'findAll'")
    List<MenuEntity> findAll();

    @Cacheable(value = "MenuList", key = "'findAllByIdIn' + #p0.hashCode()", condition = "#p0!=null")
    List<MenuEntity> findAllByIdIn(List<Long> idList);
}