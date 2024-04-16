package xyz.needpainkiller.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.needpainkiller.base.audit.AuditService;
import xyz.needpainkiller.lib.api.ApiDispatcherServlet;

import java.util.Collections;

@Slf4j
@Configuration
public class ApiConfig {
    @Value("${api.path-prefix}")
    public String API_PREFIX;
    @Value("${api.path-pattern}")
    public String API_PATTERN;
    @Autowired
    private AuditService auditService;

    @Bean
    public ServletRegistrationBean<ApiDispatcherServlet> dispatcherRegistration() {
        ServletRegistrationBean<ApiDispatcherServlet> reg = new ServletRegistrationBean<>();
        reg.setUrlMappings(Collections.singleton(API_PREFIX));
        reg.setServlet(dispatcherServlet());
        reg.setLoadOnStartup(1);
        return reg;
    }

    @Bean(name = DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_BEAN_NAME)
    public ApiDispatcherServlet dispatcherServlet() {
        return new ApiDispatcherServlet(auditService);
    }
}
