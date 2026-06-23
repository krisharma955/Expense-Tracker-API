package com.K955.Expense_Tracker_API.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Expense Tracker API")
                                .description(
                                                "A secure and feature-rich RESTful Expense Tracker API built with Spring Boot and PostgreSQL. This application enables users to manage their income and expenses efficiently while providing powerful filtering and financial insights through summary endpoints.\n" +
                                                "\n" +
                                                "## Features\n" +
                                                "\n" +
                                                "* User registration and login using JWT Authentication\n" +
                                                "* Role-based security with Spring Security\n" +
                                                "* Create, read, update, and delete transactions\n" +
                                                "* Search transactions by keyword\n" +
                                                "* Pagination and sorting support\n" +
                                                "* Dynamic filtering by category, type, and date range using JPA Specifications\n" +
                                                "* Overall financial summary including total income, total expenses, and balance\n" +
                                                "* Category-wise expense analysis\n" +
                                                "* DTO-based architecture with MapStruct\n" +
                                                "* Global exception handling and request validation\n" +
                                                "* Interactive API documentation with Swagger/OpenAPI\n" +
                                                "* Dockerized application for easy deployment\n" +
                                                "* Cloud deployment support\n" +
                                                "\n" +
                                                "## Tech Stack\n" +
                                                "\n" +
                                                "* Java 21\n" +
                                                "* Spring Boot\n" +
                                                "* Spring Security\n" +
                                                "* JWT\n" +
                                                "* Spring Data JPA\n" +
                                                "* PostgreSQL\n" +
                                                "* MapStruct\n" +
                                                "* Swagger/OpenAPI\n" +
                                                "* Docker\n" +
                                                "* Render\n" +
                                                "\n" +
                                                "## Key Concepts Implemented\n" +
                                                "\n" +
                                                "* Authentication and Authorization\n" +
                                                "* Dynamic Queries with JPA Specifications\n" +
                                                "* Pagination, Sorting, and Searching\n" +
                                                "* Aggregate Queries using JPQL\n" +
                                                "* Group By Operations\n" +
                                                "* DTO Mapping with MapStruct\n" +
                                                "* Global Exception Handling\n" +
                                                "* Environment Variables and Configuration Management\n" +
                                                "* Containerization with Docker\n" +
                                                "* Cloud Deployment\n"
                                )
                                .version("1.0.0")
                );
    }


}
