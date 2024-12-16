package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@RestController
@RequestMapping("/database")
public class UserDatabaseApplication {

    private static final String FILE_PATH = "users.json";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(UserDatabaseApplication.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "8082"));
        app.run(args);
        SpringApplication.run(UserDatabaseApplication.class, args);
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/users")
    public List<Map<String, Object>> getUsers() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) return new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return new ObjectMapper().readValue(reader, List.class);
        }
    }

    @PostMapping("/users")
    
    public Map<String, Object> addUser(@RequestBody Map<String, Object> user) throws IOException {
        List<Map<String, Object>> users = getUsers();
        users.add(user);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            new ObjectMapper().writeValue(writer, users);
        }
        return user;
    }
    
}