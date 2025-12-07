package com.planit_square.holiday_service.common;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local Server")
                ));
    }

    private Info apiInfo() {
        return new Info()
                .title("Planit Square API Documentation")
                .description("Planit Square 프로젝트의 REST API 명세서입니다.")
                .version("v1.0.0");
    }
}
