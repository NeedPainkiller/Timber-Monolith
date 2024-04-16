package xyz.needpainkiller.lib.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
@Profile("rabbitmq")
@Service
@Slf4j
public class MessageQueueService {

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    @Qualifier("objectMapper")
    private ObjectMapper objectMapper;

    public Queue createQueue(String queueName) {
        Queue queue = new Queue(queueName, true);
        amqpAdmin.declareQueue(queue);
        Binding binding = BindingBuilder.bind(queue).to(directExchange).with(queueName);
        amqpAdmin.declareBinding(binding);
        return queue;
    }

    public boolean isQueueExists(String queueName) {
        QueueInformation information = amqpAdmin.getQueueInfo(queueName);
        return information != null;
    }

    public void deleteQueue(String queueName) {
        amqpAdmin.deleteQueue(queueName);
    }

    public void purgeQueue(String queueName) {
        amqpAdmin.purgeQueue(queueName);
    }


    public void sendToQueue(String queueName, String message) {
        if (Strings.isBlank(message)) {
            return;
        }
        Message msg = new Message(message.getBytes(StandardCharsets.UTF_8));
        rabbitTemplate.convertAndSend(directExchange.getName(), queueName, msg);
    }

    public void sendToQueue(String queueName, Serializable message) {
        if (message == null) {
            return;
        }
        try {
            byte[] byteMsg = objectMapper.writeValueAsBytes(message);
            Message msg = new Message(byteMsg);
            rabbitTemplate.convertAndSend(directExchange.getName(), queueName, msg);
        } catch (JsonProcessingException e) {
            log.error("sendToQueue : {}", e.getMessage());
        }
    }
}
