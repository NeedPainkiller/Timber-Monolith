package xyz.needpainkiller.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;


@Slf4j
@Configuration
public class SwaggerConfig {
    static {
        SpringDocUtils.getConfig()
                .replaceWithClass(LocalDateTime.class, String.class)
                .replaceWithClass(LocalDate.class, String.class)
                .replaceWithClass(LocalTime.class, String.class)
                .replaceWithClass(ZonedDateTime.class, String.class);
    }

    @Value("${version}")
    private String version;

    private Info apiInfo() {
        Contact contact = new Contact().name("kam6512").email("kam6512@google.com").url("https://home.needpainkiller.xyz/");
        return new Info()
                .title("TIMBER API Server")
                .description("TIMBER API Server")
                .version(version)
                .contact(contact);
    }

    @Bean
    public OpenAPI rootApi() {
        final String securitySchemeName = "X-Authorization";

        SecurityScheme jwtSecurityScheme = new SecurityScheme()
                .name(securitySchemeName)
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(securitySchemeName);

        return new OpenAPI()
                .info(apiInfo())
                .components(new Components().addSecuritySchemes(securitySchemeName, jwtSecurityScheme))
                .addSecurityItem(securityRequirement)
                ;
    }

    @Bean
    public GroupedOpenApi v1() {
        return GroupedOpenApi.builder()
                .group("v1").displayName("v1")
                .pathsToMatch("/api/v1/**")
                .build();
    }
}
