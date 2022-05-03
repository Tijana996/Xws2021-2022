package app.configuration;

import app.handlers.RestTemplateErrorHandler;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplatesConfig {

    @Bean("usersServiceRestTemplate")
    public RestTemplate usersServiceRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://localhost:8082/").errorHandler(new RestTemplateErrorHandler()).build();
    }

    @Bean("postServicesRestTemplate")
    public RestTemplate postsServicesRestTemplate(RestTemplateBuilder builder) {
        return builder.rootUri("http://localhost:8081/").errorHandler(new RestTemplateErrorHandler()).build();
    }
}
