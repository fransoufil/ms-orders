package com.fransoufil.ms_orders.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Orders API")
                        .version("v1")
                        .description("API for Orders controll")
                        .termsOfService("https://www.github.fransoufil.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.github.fransoufil.com")));
    }
}
