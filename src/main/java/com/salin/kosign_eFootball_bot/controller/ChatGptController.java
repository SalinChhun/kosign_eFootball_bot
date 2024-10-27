package com.salin.kosign_eFootball_bot.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
public class ChatGptController extends KosignEfootballBotResController{

    @Value("${chatgpt.model}")
    String model;

    @Value("${chatgpt.api.url}")
    String apiUrl;

    @Value("${chatgpt.api.key}")
    String apiKey;

    private static RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "ask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> ask(@RequestParam String query, @RequestParam String imageUrl) {
        // Create the request body
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);

        // Create the messages array
        Map<String, Object> messageContent = new HashMap<>();
        messageContent.put("role", "user");

        // Create the content array
        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "text");
        textContent.put("text", query);

        Map<String, Object> imageContent = new HashMap<>();
        imageContent.put("type", "image_url");
        Map<String, String> imageUrlMap = new HashMap<>();
        imageUrlMap.put("url", imageUrl);
        imageContent.put("image_url", imageUrlMap);

        // Combine text and image content
        messageContent.put("content", new Object[]{textContent, imageContent});

        // Add the messages to the request body
        requestBody.put("messages", new Object[]{messageContent});
        requestBody.put("max_tokens", 300);

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        // Create the HttpEntity containing the headers and the request body
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Make the API call
            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (HttpClientErrorException e) {
            // Handle specific error codes
            if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                        .body("You have exceeded your API quota. Please check your plan.");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

}