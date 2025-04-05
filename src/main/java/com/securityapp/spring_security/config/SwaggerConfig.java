package com.securityapp.spring_security.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.http.HttpHeaders;

@OpenAPIDefinition(
        info = @Info(
                title = "API SECURITY",
                description = "This project is a User Management CRUD that includes role-based access control and " +
                        "authentication using JWT (JSON Web Tokens) for secure API endpoints. The system allows" +
                        " administrators to create, update, delete, and manage users, ensuring that only authorized " +
                        "users can perform specific operations according to their assigned roles.\n" +
                        "\n" +
                        "\uD83D\uDD39 Key Features:\n" +
                        "Spring Boot & Java: Backend implementation with a clean and scalable architecture.\n" +
                        "\n" +
                        "Spring Security & JWT: Secure authentication and authorization system.\n" +
                        "\n" +
                        "Role-Based Access Control (RBAC): Different user roles with specific permissions.\n" +
                        "\n" +
                        "MySQL Database: Persistent data storage for user management.\n" +
                        "\n" +
                        "Swagger Documentation: API documentation for easy testing and integration.\n" +
                        "\n" +
                        "Unit Testing with JUnit & Mockito: Comprehensive testing to ensure reliability and maintainability.\n" +
                        "\n" +
                        "H2 In-Memory Database: Simplified API testing without external dependencies.\n" +
                        "\n" +
                        "This project is designed as a portfolio piece, demonstrating best practices in Java development," +
                        " security, API design, and testing.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Daniel iwach",
                        url = "https://www.linkedin.com/in/daniel-iwach",
                        email = "daniel.g.iwach@gmail.com"
                )

        ),servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8080"
                )
        },security = @SecurityRequirement(
                name = "Security Token"
)
)
@SecurityScheme(
        name = "Security Token",
        description = "access Token for my API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {
}
