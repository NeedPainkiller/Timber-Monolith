package xyz.needpainkiller.api.file;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import xyz.needpainkiller.api.file.model.Files;

@Profile("kafka")
@Slf4j
@Component
@KafkaListener(groupId = "timber", topics = "timber__topic-file-downloaded")
public class KafkaFileDownloadListener {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileService fileService;

    @KafkaHandler
    public void handleMessage(@NonNull @Payload String inputString, @Header(KafkaHeaders.OFFSET) String offset) {
        try {
            Files files = objectMapper.readValue(inputString, Files.class);
            log.info(" Received: {}", files);
            fileService.increaseFileDownloadCnt(files);
        } catch (JsonProcessingException e) {
            log.error("Error processing message", e);
        }
    }
}

