package xyz.needpainkiller.config.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.util.backoff.FixedBackOff;
import xyz.needpainkiller.api.audit.model.AuditLogMessage;

import java.util.HashMap;
import java.util.Map;

@Profile("kafka")
@Configuration
@Slf4j
public class KafkaConfig {


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

    /**
     * Kafka Message Converter
     * - JsonMessageConverter : JSON 메시지 컨버터
     */
    @Bean
    public RecordMessageConverter converter() {
        JsonMessageConverter converter = new JsonMessageConverter();
        DefaultJackson2JavaTypeMapper jackson2JavaTypeMapper = new DefaultJackson2JavaTypeMapper();
        jackson2JavaTypeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        jackson2JavaTypeMapper.addTrustedPackages("xyz.needpainkiller.api.audit.model");
        Map<String, Class<?>> mappings = new HashMap<>();
        mappings.put("loginLogMessages", AuditLogMessage.class);
        mappings.put("auditLogMessages", AuditLogMessage.class);
        jackson2JavaTypeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(jackson2JavaTypeMapper);
        return converter;
    }

    @Bean
    public NewTopic loginLogMessages() {
        return new NewTopic("loginLogMessages", 1, (short) 1);
    }
    @Bean
    public NewTopic auditLogMessages() {
        return new NewTopic("auditLogMessages", 1, (short) 1);
    }
}
