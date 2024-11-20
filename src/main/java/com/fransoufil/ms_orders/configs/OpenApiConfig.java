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
                        .title("Curso Udemy Spring-Boot-Ionic Nelio Alves")
                        .version("v1")
                        .description("API para Gestao de Clientes, Produtos, Pedidos e Pagamentos")
                        .termsOfService("https://www.github.fransoufil.com")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.github.fransoufil.com")));
    }
}
