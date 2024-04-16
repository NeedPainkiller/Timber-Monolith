package xyz.needpainkiller.lib.amqp;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Getter
@Profile("rabbitmq")
@Component
@Slf4j
public class Receiver {

    private final CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        log.info("Received : {}", message);
        latch.countDown();
    }

}