package xyz.needpainkiller.config.cache;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.TimeoutOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.time.Duration;
import java.util.List;

@Slf4j
@Configuration
@Profile("redis")
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.password}")
    private String password;

    private final Duration SOCKET_TIMEOUT = Duration.ofSeconds(10);
    private final Duration COMMAND_TIMEOUT = Duration.ofSeconds(3);

    private final SocketOptions socketOptions = SocketOptions.builder()
            .connectTimeout(SOCKET_TIMEOUT)
            .keepAlive(true)
            .build();

    private final TimeoutOptions timeoutOptions = TimeoutOptions.enabled(COMMAND_TIMEOUT);
    private final ClientOptions clientOptions = ClientOptions.builder()
            .socketOptions(socketOptions)
            .timeoutOptions(timeoutOptions)
            .autoReconnect(true)

            .cancelCommandsOnReconnectFailure(true)
            .suspendReconnectOnProtocolFailure(true)

            .publishOnScheduler(true)
            .disconnectedBehavior(ClientOptions.DisconnectedBehavior.DEFAULT)
            .build();

    @Profile("!redis-cluster")
    @Bean(name = "redisConnectionFactory")
    public LettuceConnectionFactory localRedisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        if (Strings.isNotBlank(password)) {
            redisStandaloneConfiguration.setPassword(password);
        }
        LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(COMMAND_TIMEOUT)
                .shutdownTimeout(SOCKET_TIMEOUT)
                .shutdownQuietPeriod(SOCKET_TIMEOUT)
                .clientOptions(clientOptions)
                .build();
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }

    @Value("${spring.redis.cluster.nodes}")
    private List<String> clusterNodes;

    @Profile("redis-cluster")
    @Bean(name = "redisConnectionFactory")
    public LettuceConnectionFactory clusterRedisConnectionFactory() {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .commandTimeout(COMMAND_TIMEOUT)
                .shutdownTimeout(Duration.ZERO)
                .readFrom(ReadFrom.REPLICA_PREFERRED) // 복제본 노드에서 읽지 만 사용할 수없는 경우 마스터에서 읽습니다.
                .clientOptions(clientOptions)
                .build();

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterNodes);
        if (Strings.isNotBlank(password)) {
            redisClusterConfiguration.setPassword(password);
        }
        return new LettuceConnectionFactory(redisClusterConfiguration, clientConfiguration);
    }


    private final String PATTERN = "__keyevent@*__:expired";

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, RedisEventLogger expirationListener) {
        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(expirationListener, new PatternTopic(PATTERN));
        redisMessageListenerContainer.setErrorHandler(e -> log.error("There was an error in redis key expiration listener container", e));
        return redisMessageListenerContainer;
    }
}