package com.example.restfulapi;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestfulApiApplication {

    public static void main(String[] args) {

        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("APP_PASSWORD", dotenv.get("APP_PASSWORD"));
        System.setProperty("EMAIL_SERVER", dotenv.get("EMAIL_SERVER"));
        System.setProperty("DB_URL", dotenv.get("DB_URL"));
        System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
        System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));

        SpringApplication.run(RestfulApiApplication.class, args);
    }

}
