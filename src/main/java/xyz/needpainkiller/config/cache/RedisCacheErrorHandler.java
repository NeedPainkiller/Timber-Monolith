package xyz.needpainkiller.config.cache;

import io.lettuce.core.RedisCommandTimeoutException;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.data.redis.RedisConnectionFailureException;

@Slf4j
public class RedisCacheErrorHandler implements CacheErrorHandler {

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (cause instanceof RedisConnectionException) {
            log.error("RedisConnectionException : {}", message);
            return;
        }
        if (cause instanceof RedisConnectionFailureException) {
            log.error("RedisConnectionFailureException : {}", message);
            return;
        }
//        if (cause instanceof QueryTimeoutException) {
//            log.error("QueryTimeoutException : {}", message);
//            return;
//        }
        if (cause instanceof RedisCommandTimeoutException) {
            log.error("RedisCommandTimeoutException : {}", message);
            return;
        }
        throw exception;
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (cause instanceof RedisConnectionException) {
            log.error("RedisConnectionException : {}", message);
            return;
        }
        if (cause instanceof RedisConnectionFailureException) {
            log.error("RedisConnectionFailureException : {}", message);
            return;
        }
        if (cause instanceof RedisCommandTimeoutException) {
            log.error("RedisCommandTimeoutException : {}", message);
            return;
        }
        throw exception;
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (cause instanceof RedisConnectionException) {
            log.error("RedisConnectionException : {}", message);
            return;
        }
        if (cause instanceof RedisConnectionFailureException) {
            log.error("RedisConnectionFailureException : {}", message);
            return;
        }
        if (cause instanceof RedisCommandTimeoutException) {
            log.error("RedisCommandTimeoutException : {}", message);
            return;
        }
        throw exception;
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        Throwable cause = exception.getCause();
        String message = exception.getMessage();
        if (cause instanceof RedisConnectionException) {
            log.error("RedisConnectionException : {}", message);
            return;
        }
        if (cause instanceof RedisConnectionFailureException) {
            log.error("RedisConnectionFailureException : {}", message);
            return;
        }
        if (cause instanceof RedisCommandTimeoutException) {
            log.error("RedisCommandTimeoutException : {}", message);
            return;
        }
        throw exception;
    }
}