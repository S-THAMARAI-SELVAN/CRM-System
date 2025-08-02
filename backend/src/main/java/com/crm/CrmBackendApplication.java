package com.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:3000")
public class CrmBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmBackendApplication.class, args);
        System.out.println("=================================");
        System.out.println("🚀 CRM Backend API Started!");
        System.out.println("📍 API Base URL: http://localhost:8080/api");
        System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
        System.out.println("=================================");
    }
}
