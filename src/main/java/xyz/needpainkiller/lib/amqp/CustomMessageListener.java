package xyz.needpainkiller.lib.amqp;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
@Profile("rabbitmq")
@Service
public class CustomMessageListener {

/*    @RabbitListener(queues = "spring-boot")
    public void receiveMessage(final Message message) {
        System.out.println(message);
    }*/

}