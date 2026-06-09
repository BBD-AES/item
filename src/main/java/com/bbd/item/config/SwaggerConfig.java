package com.bbd.item.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Item API")
                        .description("Item Application API Documentation")
                        .version("v1.0"))
                .addServersItem(new Server()
                        .url("http://localhost:8082/item")
                        .description("Local 내 컴퓨터"))

                .addServersItem(new Server()
                        .url("http://100.73.142.41/item")
                        .description("강의실 노트북"))

                .addServersItem(new Server()
                        .url("http://192.168.200.220/item")
                        .description("Nginx"));

    }
}