package com.example.demo;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@RequestMapping("/users")
public class UserAPIApplication {
    

    private final RestTemplate restTemplate = new RestTemplate();
    private final String DATABASE_URL = "http://localhost:8081/database";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UserDatabaseApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8081"));
        app.run(args);
        SpringApplication.run(UserAPIApplication.class, args);
    }

    @SuppressWarnings("unchecked")
    @GetMapping
    public List<Map<String, Object>> getUsers() {
        return restTemplate.getForObject(DATABASE_URL + "/users", List.class);
    }

    @SuppressWarnings("unchecked")
    @PostMapping
    public Map<String, Object> addUser(@RequestBody Map<String, Object> user) {
        return restTemplate.postForObject(DATABASE_URL + "/users", user, Map.class);
    }
}
