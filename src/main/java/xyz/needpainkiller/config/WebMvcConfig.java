package xyz.needpainkiller.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.annotation.MultipartConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xyz.needpainkiller.lib.HtmlCharacterEscapes;
import xyz.needpainkiller.lib.api.ApiInterceptor;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Configuration
@EnableWebMvc
@MultipartConfig
@ComponentScan
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${spring.base-url}")
    private String baseUrl;
    @Value("${api.path-pattern}")
    private String API_PATH;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ApiInterceptor apiInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiInterceptor)
                .addPathPatterns(API_PATH);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

//        Gson gson = new Gson();
//        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
//        gsonHttpMessageConverter.setGson(gson);
//        converters.add(gsonHttpMessageConverter);

        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
        converters.add(stringHttpMessageConverter);

        ObjectMapper copy = objectMapper.copy();
        copy.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(copy);
        converters.add(jackson2HttpMessageConverter);

//        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    /*

        private static final String[] CLASSPATH_RESOURCE_LOCATIONS =
                {"classpath:/META-INF/resources/", "classpath:/resources/", "classpath:/static/", "classpath:/public/"};

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/**")
                    .addResourceLocations("classpath:/static/")
    //                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS)
                    .setCacheControl(CacheControl.noCache())
                    .setCachePeriod(0)
                    .resourceChain(true)
                    .addResolver(new EncodedResourceResolver())
                    .addResolver(new PathResourceResolver())
            ;

        }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/swagger").setViewName("forward:/swagger-ui/index.html");
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("").setViewName("forward:/index.html");
    }

    */
/*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("addCorsMappings");
        registry.addMapping("/api/**")
                .allowedHeaders("*")
                .allowedOrigins(baseUrl, "http://localhost:8080", "http://localhost:3000")
                .allowedMethods("GET", "PUT", "POST", "DELETE", "OPTIONS")
                .maxAge(60)   // add maxAge
                .allowCredentials(false);

//        registry.addMapping("/ws/*")
//                .allowedHeaders("*")
//                .allowedOrigins("*")
//                .maxAge(3600)   // add maxAge
//                .allowedOriginPatterns("")
//                .allowCredentials(true);
    }
*/

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(AsyncTaskConfig.mvcExecutor());
        configurer.setDefaultTimeout(30000L);
    }
}
