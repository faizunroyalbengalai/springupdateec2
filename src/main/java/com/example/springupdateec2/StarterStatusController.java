package com.example.springupdateec2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

@RestController
public class StarterStatusController {

    @Autowired(required = false)
    private MongoTemplate mongoTemplate;

    @GetMapping("/api/status")
    public Map<String, String> status() {
        Map<String, String> payload = new LinkedHashMap<>();
        if (mongoTemplate == null) {
            payload.put("status", "disconnected");
            payload.put("title", "Database Not Configured");
            payload.put("detail", "No MongoTemplate bean is available.");
            return payload;
        }
        try {
            String dbName = mongoTemplate.getDb().getName();
            payload.put("status", "connected");
            payload.put("title", "Database Connected");
            payload.put("detail", "MongoDB • " + dbName);
        } catch (Exception e) {
            payload.put("status", "disconnected");
            payload.put("title", "Database Unreachable");
            payload.put("detail", e.getMessage());
        }
        return payload;
    }
}
