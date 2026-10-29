package com.tech.challenge.fiap.gestao.restaurante.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Gestão de Restaurante")
                        .version("1.0.0")
                        .description("Documentação da API para gestão de restaurantes, usuários e itens do cardápio."))
                .servers(List.of(
                        new Server().url("/api/v1").description("Servidor principal")
                ));
    }
}
