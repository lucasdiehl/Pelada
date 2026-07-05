package com.pelada.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI goalkeeperApi() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(new Info()
                        .title("Pelada API")
                        .description("API para organização de partidas e contratação de goleiros.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Lucas Diehl")
                                .email("lucas@email.com"))
                        .license(new License()
                                .name("MIT"))
                )
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(securitySchemeName)
                )
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Local"),
                        new Server()
                                .url("https://api.pelada.com")
                                .description("Production")
                ));
    }
}
