package com.surely.challenge.adapter.service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Surely Shopping Cart API")
                        .version("1.0.0")
                        .description("API REST para la gestión de carritos de compras, cálculo de promociones y reportes de consumo.")
                        .contact(new Contact()
                                .name("Jose Soria")
                                .email("jose.soria@mail.com")));
    }
}
