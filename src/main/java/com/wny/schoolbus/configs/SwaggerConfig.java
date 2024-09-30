package com.wny.schoolbus.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${server.url-1}")
    private String url1;

    private static final String SECURITY_SCHEME_NAME = "Bearer Token";
    private static final String SECURITY_SCHEME = "bearer";
    private static final String BEARER_FORMAT = "JWT";
    private static final String VERSION = "version 3.0";
    private static final String DESCRIPTION = "PASV SHOP Service";
    private static final String TERMS_OF_SERVICE = "Terms of service";

    @Bean
    public OpenAPI openApiConfiguration() {
        Server server1 = new Server();
        server1.setUrl(url1);


        return new OpenAPI()
                .addServersItem(server1)
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(
                        new Components().addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme(SECURITY_SCHEME)
                                        .bearerFormat(BEARER_FORMAT)))
                .info(new Info()
                        .version(VERSION)
                        .description(DESCRIPTION)
                        .termsOfService(TERMS_OF_SERVICE)
                );
    }
}
