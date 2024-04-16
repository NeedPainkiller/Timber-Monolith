package xyz.needpainkiller.config.cache;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventType;
import org.springframework.context.annotation.Profile;

@Slf4j
@Profile("ehcache")
public class EhCacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        log.debug("key: {} \n old: {} \n new:{}", cacheEvent.getKey(), cacheEvent.getOldValue(), cacheEvent.getNewValue());
        if (cacheEvent.getType().equals(EventType.CREATED) || cacheEvent.getType().equals(EventType.UPDATED)) {
            log.debug("CACHE CREATED : {}", cacheEvent.getKey());
        }
    }
}