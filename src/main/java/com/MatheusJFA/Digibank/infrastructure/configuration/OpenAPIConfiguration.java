package com.MatheusJFA.Digibank.infrastructure.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Servidor de desenvolvimento"
                ),
                @Server(
                        url = "http://localhost:8080/context-path/swagger-ui.html",
                        description = "Interface do Swagger UI"
                ),
                @Server(
                        url = "http://localhost:8080/v3/api-docs",
                        description = "Documentação da API em formato OpenAPI 3.0"
                )
        },
        info = @Info(
                title = "Digibank API",
                version = "v1.0.0",
                description = "API for Digibank application",
                contact = @Contact(
                        name = "Matheus José F. de Aguiar",
                        email = "matheusjfa@outlook.com"
                ),
                license = @License(
                        name = "MIT License",
                        url = "https://opensource.org/license/mit/"
                )
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth")
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        in = SecuritySchemeIn.HEADER
)
public class OpenAPIConfiguration {
}
