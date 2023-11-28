package com.example.api_java;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Sale smartphone API docs",
                description = "" +
                        "API Ban Dien Thoai",
                contact = @Contact(
                        name = "",
                        url = "",
                        email = "lequanghungb2@gmail.com"
                ),
                license = @License(
                        name = "MIT Licence",
                        url = "https://github.com/thombergs/code-examples/blob/master/LICENSE")),
        servers = @Server(url = "/"),
        security = {@SecurityRequirement(name = "bearerToken")}

)
@SecuritySchemes({
        @SecurityScheme(
                name = "bearerToken",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})


public class ApiJavaApplication {
    @Autowired
    private DataSource dataSource;
    public void checkDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            if (connection != null) {
                System.out.println("Connected to the database.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiJavaApplication.class, args);
    }
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    @Bean
    public ModelMapper modelMapper() {
        checkDatabaseConnection();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }



}

