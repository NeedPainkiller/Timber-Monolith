package xyz.needpainkiller.api.authentication.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import xyz.needpainkiller.api.authentication.model.Api;

import java.util.List;


public interface ApiRepo extends JpaRepository<Api, Long> {
    @Cacheable(value = "ApiList", key = "'findAll'")
    List<Api> findAll();

    @Cacheable(value = "ApiList", key = "'findByInId-' + #p0.hashCode()", condition = "#p0!=null")
    List<Api> findByIdIn(List<Long> idList);

    @Cacheable(value = "ApiList", key = "'findByMenuPk-' + #p0", condition = "#p0!=null")
    List<Api> findByMenuPk(Long menuPk);

    @Cacheable(value = "ApiList", key = "'findByMenuPkIn-' + #p0.hashCode()", condition = "#p0!=null")
    List<Api> findByMenuPkIn(List<Long> menuPkList);
}