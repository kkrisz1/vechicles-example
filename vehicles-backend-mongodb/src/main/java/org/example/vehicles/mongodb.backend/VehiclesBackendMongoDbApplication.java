package org.example.vehicles.mongodb.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class VehiclesBackendMongoDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(VehiclesBackendMongoDbApplication.class, args);
    }
}
