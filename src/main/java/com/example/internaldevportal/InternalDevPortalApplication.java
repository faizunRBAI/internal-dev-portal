package com.example.internaldevportal;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        title = "Internal Developer Portal API",
        version = "1.0",
        description = "Enterprise IDP — Projects, Teams, Environments & Deployments"))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT")
public class InternalDevPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(InternalDevPortalApplication.class, args);
    }
}
