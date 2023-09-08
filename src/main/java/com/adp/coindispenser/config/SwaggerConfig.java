package com.adp.coindispenser.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Lokesh Venktesan
 */

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {


    @Value("${info.application.version}")
    private String versionNumber;


    @Value("${info.application.name}")
    private String serviceName;

    @Bean
    public OpenAPI bsmOpenAPI() {
        return new OpenAPI()
                .info(new Info().title(serviceName)
                        .title("Rest APIs of " + serviceName)
                        .description("Interview question")
                        .version(versionNumber));
    }


}
