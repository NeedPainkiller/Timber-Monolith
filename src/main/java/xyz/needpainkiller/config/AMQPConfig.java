package xyz.needpainkiller.config;

import com.rabbitmq.client.ShutdownSignalException;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import xyz.needpainkiller.lib.amqp.Receiver;

@Profile("rabbitmq")
@Configuration
@EnableRabbit
@Slf4j
public class AMQPConfig {
    private static final String topicExchangeName = "timberl-exchange";

    @Bean
    public DirectExchange exchange() {
        DirectExchange exchange = new DirectExchange(topicExchangeName, true, false);
        log.info("{} > durable : {}", exchange.getName(), exchange.isDurable());
        return exchange;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitAdmin amqpAdmin() {
        return new RabbitAdmin(rabbitConnectionFactory());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.rabbitmq")
    public CachingConnectionFactory rabbitConnectionFactory() {
        return new CachingConnectionFactory() {
            @Override
            public void shutdownCompleted(@NotNull ShutdownSignalException cause) {
                log.info("rabbitmq shutdownCompleted : {}", cause.getMessage());
            }

        };
    }


    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
