//package com.salin.kosign_eFootball_bot.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class ChatGptService {
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    private final String apiUrl = "https://api.openai.com/v1/chat/completions"; // OpenAI API endpoint
//    private final String apiKey = "your_api_key_here"; // Replace with your OpenAI API key
//
//    public String getChatGptResponse(String userInput) {
//        // Create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + apiKey);
//        headers.set("Content-Type", "application/json");
//
//        // Create the request body
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("model", "gpt-3.5-turbo"); // Specify the model
//        requestBody.put("messages", new Object[]{new HashMap<String, String>() {{
//            put("role", "user");
//            put("content", userInput);
//        }}});
//
//        // Create HttpEntity with headers and body
//        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//        // Make the API call
//        ResponseEntity<Map> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, Map.class);
//
//        // Extract the response content
//        Map<String, Object> choices = (Map<String, Object>) response.getBody().get("choices");
//        String chatGptResponse = (String) ((Map<String, Object>) choices.get(0)).get("message").get("content");
//
//        return chatGptResponse;
//    }
//}