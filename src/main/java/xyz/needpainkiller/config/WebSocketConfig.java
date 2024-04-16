package xyz.needpainkiller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer, WebSocketConfigurer {
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(rpaDeviceStatusHandler, "/ws/device-status")
//                .addInterceptors(new HttpSessionHandshakeInterceptor())
//                .setAllowedOrigins("*")
//                .setAllowedOriginPatterns("*")
//                .withSockJS()
//        ;
//
    }


//    @Override
//    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
//        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
//        converter.setObjectMapper(objectMapper);
//        converter.setSerializedPayloadClass(String.class);
//        converter.setStrictContentTypeMatch(true);
//        messageConverters.add(converter);
//        return false;
//    }


}
