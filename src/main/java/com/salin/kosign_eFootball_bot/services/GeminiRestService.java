package com.salin.kosign_eFootball_bot.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salin.kosign_eFootball_bot.TelegramBot;
import com.salin.kosign_eFootball_bot.payload.MatchResultResponse;
import com.salin.kosign_eFootball_bot.utils.AppLogManager;
import com.salin.kosign_eFootball_bot.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
public class GeminiRestService {

    @Value("${gemini.api.api-key}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;


    public GeminiRestService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder
                .baseUrl("https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent")
                .build();
        this.objectMapper = objectMapper;
    }

    public Optional<MatchResultResponse> predictImage(MultipartFile file) {
        try {
            // Encode image to base64
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // Prepare request body
            Map<String, Object> inlineData = new HashMap<>();
            inlineData.put("mimeType", "image/jpeg");
            inlineData.put("data", base64Image);

            Map<String, Object> part1 = new HashMap<>();
            part1.put("inlineData", inlineData);

            Map<String, Object> part2 = new HashMap<>();
            part2.put("text", "Extract all text visible in the image as json format like below: { \n" +
                    "    \"matchResult\": { \n" +
                    "        \"homeTeam\": \"\",\n" +
                    "        \"awayTeam\": \"\", \n" +
                    "        \"homeScore\": \"\",\n" +
                    "        \"awayScore\": \"\",\n" +
                    "        \"homePossession\": \"\",\n" +
                    "        \"awayPossession\": \"\",\n" +
                    "        \"homeShots\": \"\",\n" +
                    "        \"awayShots\": \"\",\n" +
                    "        \"homeShotsOnTarget\": \"\",\n" +
                    "        \"awayShotsOnTarget\": \"\",\n" +
                    "        \"homeFouls\": \"\",\n" +
                    "        \"awayFouls\": \"\",\n" +
                    "        \"homeOffsides\": \"\",\n" +
                    "        \"awayOffsides\": \"\",\n" +
                    "        \"homeCornerKicks\": \"\",\n" +
                    "        \"awayCornerKicks\": \"\",\n" +
                    "        \"homeFreeKicks\": \"\",\n" +
                    "        \"awayFreeKicks\": \"\",\n" +
                    "        \"homePasses\": \"\",\n" +
                    "        \"awayPasses\": \"\",\n" +
                    "        \"homeSuccessfulPasses\": \"\",\n" +
                    "        \"awaySuccessfulPasses\": \"\",\n" +
                    "        \"homeCrosses\": \"\",\n" +
                    "        \"awayCrosses\": \"\",\n" +
                    "        \"homeInterceptions\": \"\",\n" +
                    "        \"awayInterceptions\": \"\",\n" +
                    "        \"homeTackles\": \"\",\n" +
                    "        \"awayTackles\": \"\",\n" +
                    "        \"homeSaves\": \"\",\n" +
                    "        \"awaySaves\": \"\"\n" +
                    "    }\n" +
                    "}");

            List<Map<String, Object>> parts = Arrays.asList(part1, part2);

            Map<String, Object> content = new HashMap<>();
            content.put("parts", parts);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", Collections.singletonList(content));

            // Make API call
            String responseBody = webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(); // Blocking call, consider using reactive approach in production

            // Extract teams from response
            return extractMatchResult(responseBody);

        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<MatchResultResponse> extractMatchResult(String jsonString) throws JsonProcessingException {

        // write log response from gemini
        AppLogManager.info(TelegramBot.class, "\n[Response Data From Gemini] \n" + ObjectUtils.writeValueAsSingleLineString(jsonString) + "\n");

        jsonString = preprocessJson(jsonString);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(jsonString);
        JsonNode candidates = root.get("candidates");
        JsonNode content = candidates.get(0).get("content");
        JsonNode parts = content.get("parts");
        JsonNode text = parts.get(0).get("text");

        // Remove the "```json\n" and "\n```" from the text
        String matchResultJson = text.asText().replace("```json\n", "").replace("\n```", "");

        // Parse the match result JSON
        JsonNode matchResult = mapper.readTree(matchResultJson);
        JsonNode matchResultData = matchResult.get("matchResult");

        // Extract the match result data
        String homeTeam = matchResultData.get("homeTeam").asText();
        String awayTeam = matchResultData.get("awayTeam").asText();
        String homeScore = matchResultData.get("homeScore").asText();
        String awayScore = matchResultData.get("awayScore").asText();
        String homePossession = matchResultData.get("homePossession").asText();
        String awayPossession = matchResultData.get("awayPossession").asText();
        String homeShots = matchResultData.get("homeShots").asText();
        String awayShots = matchResultData.get("awayShots").asText();
        String homeShotsOnTarget = matchResultData.get("homeShotsOnTarget").asText();
        String awayShotsOnTarget = matchResultData.get("awayShotsOnTarget").asText();
        String homeFouls = matchResultData.get("homeFouls").asText();
        String awayFouls = matchResultData.get("awayFouls").asText();
        String homeOffsides = matchResultData.get("homeOffsides").asText();
        String awayOffsides = matchResultData.get("awayOffsides").asText();
        String homeCornerKicks = matchResultData.get("homeCornerKicks").asText();
        String awayCornerKicks = matchResultData.get("awayCornerKicks").asText();
        String homeFreeKicks = matchResultData.get("homeFreeKicks").asText();
        String awayFreeKicks = matchResultData.get("awayFreeKicks").asText();
        String homePasses = matchResultData.get("homePasses").asText();
        String awayPasses = matchResultData.get("awayPasses").asText();
        String homeSuccessfulPasses = matchResultData.get("homeSuccessfulPasses").asText();
        String awaySuccessfulPasses = matchResultData.get("awaySuccessfulPasses").asText();
        String homeCrosses = matchResultData.get("homeCrosses").asText();
        String awayCrosses = matchResultData.get("awayCrosses").asText();
        String homeInterceptions = matchResultData.get("homeInterceptions").asText();
        String awayInterceptions = matchResultData.get("awayInterceptions").asText();
        String homeTackles = matchResultData.get("homeTackles").asText();
        String awayTackles = matchResultData.get("awayTackles").asText();
        String homeSaves = matchResultData.get("homeSaves").asText();
        String awaySaves = matchResultData.get("awaySaves").asText();

        return Optional.ofNullable(MatchResultResponse.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .homeScore(homeScore)
                .awayScore(awayScore)
                .homePossession(homePossession)
                .awayPossession(awayPossession)
                .homeShots(homeShots)
                .awayShots(awayShots)
                .homeShotsOnTarget(homeShotsOnTarget)
                .awayShotsOnTarget(awayShotsOnTarget)
                .homeFouls(homeFouls)
                .awayFouls(awayFouls)
                .homeOffsides(homeOffsides)
                .awayOffsides(awayOffsides)
                .homeCornerKicks(homeCornerKicks)
                .awayCornerKicks(awayCornerKicks)
                .homeFreeKicks(homeFreeKicks)
                .awayFreeKicks(awayFreeKicks)
                .homePasses(homePasses)
                .awayPasses(awayPasses)
                .homeSuccessfulPasses(homeSuccessfulPasses)
                .awaySuccessfulPasses(awaySuccessfulPasses)
                .homeCrosses(homeCrosses)
                .awayCrosses(awayCrosses)
                .homeInterceptions(homeInterceptions)
                .awayInterceptions(awayInterceptions)
                .homeTackles(homeTackles)
                .awayTackles(awayTackles)
                .homeSaves(homeSaves)
                .awaySaves(awaySaves)
                .build());
    }

    public static String preprocessJson(String jsonString) {
        return jsonString.replaceAll("\\s*\"\\s*\\n\\s*", "\"")
                .replaceAll("\\s*\\n\\s*", " ")
                .replaceAll("\\s*\"\\s*,\\s*\"", "\",\"");
    }


    // New method to send a message
    public String sendMessage(String textPrompt) {
        try {
            // Prepare request body
            // Create the inner map for the "parts"
            Map<String, Object> part = new HashMap<>();
            part.put("text", textPrompt);

            // Create a list to hold the "parts"
            List<Map<String, Object>> partsList = new ArrayList<>();
            partsList.add(part);

            // Create the outer map for "contents"
            Map<String, Object> content = new HashMap<>();
            content.put("parts", partsList);

            // Create a list to hold the "contents"
            List<Map<String, Object>> contentsList = new ArrayList<>();
            contentsList.add(content);

            // Create the final message data map
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("contents", contentsList);

            // Make API call to send the message

            return webClient.post()
                    .uri(uriBuilder -> uriBuilder.queryParam("key", apiKey).build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody) // Use messageData instead of requestBody
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error sending message: " + e.getMessage();
        }
    }

}
