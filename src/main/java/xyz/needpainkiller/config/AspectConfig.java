package xyz.needpainkiller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(exposeProxy = true)
public class AspectConfig {
//    @Bean
//    public RefreshTokenAdvisor refreshTokenAdvisor() {
//        RefreshTokenAdvisor advisor = Aspects.aspectOf(RefreshTokenAdvisor.class);
//        return advisor;
//    }
}
