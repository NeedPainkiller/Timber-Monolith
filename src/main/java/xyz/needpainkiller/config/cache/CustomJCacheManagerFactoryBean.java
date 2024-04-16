package xyz.needpainkiller.config.cache;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.cache.jcache.JCacheManagerFactoryBean;
import org.springframework.lang.Nullable;

import javax.cache.CacheManager;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;

public class CustomJCacheManagerFactoryBean extends JCacheManagerFactoryBean {
    @Nullable
    private URI cacheManagerUri;

    @Nullable
    private Properties cacheManagerProperties;

    @Nullable
    private ClassLoader beanClassLoader;

    @Nullable
    private CacheManager cacheManager;


    /**
     * Specify the URI for the desired {@code CacheManager}.
     * <p>Default is {@code null} (i.e. JCache's default).
     */
    public void setCacheManagerUri(@Nullable URI cacheManagerUri) {
        this.cacheManagerUri = cacheManagerUri;
    }

    /**
     * Specify properties for the to-be-created {@code CacheManager}.
     * <p>Default is {@code null} (i.e. no special properties to apply).
     *
     * @see CachingProvider#getCacheManager(URI, ClassLoader, Properties)
     */
    public void setCacheManagerProperties(@Nullable Properties cacheManagerProperties) {
        this.cacheManagerProperties = cacheManagerProperties;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.beanClassLoader = classLoader;
    }

    @Override
    public void afterPropertiesSet() {
        CachingProvider cachingProvider = new EhcacheCachingProvider();
        this.cacheManager = cachingProvider.getCacheManager(
                this.cacheManagerUri, this.beanClassLoader, this.cacheManagerProperties);
    }


    @Override
    @Nullable
    public CacheManager getObject() {
        return this.cacheManager;
    }

    @Override
    public Class<?> getObjectType() {
        return (this.cacheManager != null ? this.cacheManager.getClass() : CacheManager.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    @Override
    public void destroy() {
        if (this.cacheManager != null) {
            this.cacheManager.close();
        }
    }
}