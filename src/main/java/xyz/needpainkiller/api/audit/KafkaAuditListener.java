package xyz.needpainkiller.api.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import xyz.needpainkiller.api.audit.model.AuditLogMessage;

@Profile("kafka")
@Slf4j
@Component
//@KafkaListener(groupId = "timber", topics = {"loginLogMessages", "auditLogMessages"})
public class KafkaAuditListener {

//    @KafkaHandler
//    public void loginLogMessages(@Payload AuditLogMessage auditLogMessage) {
//        log.info("loginLogMessage Received: " + auditLogMessage);
//    }
//
//    @KafkaHandler
//    public void auditLogMessages(@Payload AuditLogMessage auditLogMessage) {
//        log.info("auditLogMessage Received: " + auditLogMessage);
//    }
}

