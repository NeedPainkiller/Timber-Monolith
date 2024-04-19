package xyz.needpainkiller.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.ehcache.impl.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import xyz.needpainkiller.api.audit.model.AuditLogMessage;
import xyz.needpainkiller.api.file.model.Files;

import java.util.HashMap;
import java.util.Map;

@Profile("kafka")
@Configuration
@Slf4j
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    String bootstrapServers;

    /**
     * Kafka Error Handler
     * - DeadLetterPublishingRecoverer : 에러가 발생한 메시지를 Dead Letter Topic 으로 전송
     * - FixedBackOff : 1초 간격으로 2번 재시도
     */
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
    }

    @Bean
    public NewTopic loginAuditMessages() {
        return new NewTopic("timber__topic-audit-login", 1, (short) 1);
    }

    @Bean
    public NewTopic apiAuditMessages() {
        return new NewTopic("timber__topic-audit-api", 1, (short) 1);
    }

    @Bean
    public NewTopic fileStoredMessages() {
        return new NewTopic("timber__topic-file-stored", 1, (short) 1);
    }
}
