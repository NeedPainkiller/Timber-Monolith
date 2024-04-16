package xyz.needpainkiller.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

@Slf4j
@Configuration
@EnableCaching
@Profile("ehcache")
public class EhCacheConfig {

    @Bean
    public JCacheManagerFactoryBean jCacheManagerFactoryBean() throws Exception {
        JCacheManagerFactoryBean jCacheManagerFactoryBean = new CustomJCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("cache/ehcache.xml").getURI());
        return jCacheManagerFactoryBean;
    }


/*    @Bean
    public JCacheManagerFactoryBean jCacheManagerFactoryBean() throws Exception {
        JCacheManagerFactoryBean jCacheManagerFactoryBean = new JCacheManagerFactoryBean();
        jCacheManagerFactoryBean.setCacheManagerUri(new ClassPathResource("cache/ehcache.xml").getURI());
        return jCacheManagerFactoryBean;
    }*/

    @Bean
    public JCacheCacheManager jCacheCacheManager(JCacheManagerFactoryBean jCacheManagerFactoryBean) {
        JCacheCacheManager jCacheCacheManager = new JCacheCacheManager();
        jCacheCacheManager.setCacheManager(jCacheManagerFactoryBean.getObject());
        return jCacheCacheManager;
    }


}
