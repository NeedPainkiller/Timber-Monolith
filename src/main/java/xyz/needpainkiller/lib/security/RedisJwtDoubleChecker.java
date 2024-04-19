package xyz.needpainkiller.lib.security;

import io.lettuce.core.RedisConnectionException;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.HashOperations;
import xyz.needpainkiller.lib.security.error.RedisException;
import xyz.needpainkiller.lib.security.error.TokenValidFailedException;

import java.util.Map;

import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.TOKEN_EXPIRED;
import static xyz.needpainkiller.lib.exceptions.CommonErrorCode.TOKEN_MEMORY_INVALID;
import static xyz.needpainkiller.lib.exceptions.ServiceErrorCode.REDIS_CONNECTION_FAILED;

/**
 * JWT 이중 로그인 방지
 * REDIS 서버의 {botstore:user:tokens} 에 접근하여 토큰 중복 여부를 확인
 *
 * @author needpainkiller
 */

//@Component
//@Service
@Profile("redis")
@Qualifier("RedisJwtDoubleChecker")
public class RedisJwtDoubleChecker implements JwtDoubleChecker {

    private static final String EMPTY = "";
    private static final String REDIS_TOKEN_KEY = "timber:user-token";

    @Resource(name = "stringRedisTemplate")
    private HashOperations<String, String, String> tokenListOperations;

    public void validationTokenMatch(String userId, String sourceToken) {
        String originalToken = getToken(userId);
        if (!originalToken.equals(sourceToken)) {
            // 요청 Token 과 Redis 에 등록된 원본 Token 이 다른 경우
            throw new TokenValidFailedException(TOKEN_EXPIRED);
        }
    }

    public String getToken(String userId) {
        String token = null;
        try {
            Map<String, String> tokenMap = tokenListOperations.entries(REDIS_TOKEN_KEY);
            token = tokenMap.getOrDefault(userId, EMPTY);
        } catch (RedisConnectionFailureException | RedisConnectionException e) {
            throw new RedisException(REDIS_CONNECTION_FAILED);
        }
        if (token == null || token.isEmpty()) {
            throw new TokenValidFailedException(TOKEN_MEMORY_INVALID);
        }
        return token;
    }

    public void putToken(String userId, String token) {
        tokenListOperations.put(REDIS_TOKEN_KEY, userId, token);
    }


    public void deleteToken(String userId) {
        tokenListOperations.delete(REDIS_TOKEN_KEY, userId);
    }
}
