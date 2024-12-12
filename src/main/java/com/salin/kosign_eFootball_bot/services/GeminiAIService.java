package com.salin.kosign_eFootball_bot.services;

import com.google.api.gax.rpc.ApiException;
import com.salin.kosign_eFootball_bot.payload.TeamResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.Candidate;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.ContentMaker;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.PartMaker;
import com.google.cloud.vertexai.generativeai.ResponseStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiAIService {

    @Value("${gemini.api.project-id}")
    private String projectId;

    @Value("${gemini.api.location}")
    private String location;

    @Value("${gemini.api.modelName}")
    private String modelName;

    public Optional<TeamResponse> predictImage(MultipartFile file) {
        StringBuilder responseBuilder = new StringBuilder();

        try (VertexAI vertexAi = new VertexAI(projectId, location)) {
            GenerationConfig generationConfig = GenerationConfig.newBuilder()
                    .setMaxOutputTokens(8192)
                    .setTemperature(1F)
                    .setTopP(0.95F)
                    .build();

            List<SafetySetting> safetySettings = Arrays.asList(
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                            .build(),
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                            .build(),
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                            .build(),
                    SafetySetting.newBuilder()
                            .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                            .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                            .build());

            GenerativeModel model = new GenerativeModel.Builder()
                    .setModelName(modelName)
                    .setVertexAi(vertexAi)
                    .setGenerationConfig(generationConfig)
                    .setSafetySettings(safetySettings)
                    .build();

            // Convert the MultipartFile to byte[]
            byte[] imageBytes = file.getBytes();

            // Create PartMaker from image bytes
            var image = PartMaker.fromMimeTypeAndData(file.getContentType(), imageBytes);

            // Generate content using VertexAI
            var content = ContentMaker.fromMultiModalData(image, "extract the image with json format that have homeTeam and awayTeam name only");
            ResponseStream<GenerateContentResponse> responseStream = model.generateContentStream(content);

            // Process the response
            for (GenerateContentResponse response : responseStream) {
                for (Candidate candidate : response.getCandidatesList()) {
                    for (Part part : candidate.getContent().getPartsList()) {
                        String text = part.getText();
                        responseBuilder.append(text).append("\n");
                    }
                }
            }

        } catch (IOException e) {
            // Handle general IO exceptions
            return Optional.empty(); // Return an empty Optional on error
        } catch (ApiException e) {
            // Handle exceptions specific to Vertex AI API calls
            return Optional.empty(); // Return an empty Optional on error
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            return Optional.empty(); // Return an empty Optional on error
        }

        return extractTeams(responseBuilder.toString());
    }

    public static Optional<TeamResponse> extractTeams(String jsonString) {

        System.err.println("Response from AI: " + jsonString);

        jsonString = preprocessJson(jsonString);
        String pattern = "\"homeTeam\":\\s*\"([^\"]+)\"\\s*,?\\s*\"awayTeam\":\\s*\"([^\"]+)\"";

        // Create a Pattern object
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(jsonString);

        String homeTeam = null;
        String awayTeam = null;

        // Find matches
        if (matcher.find()) {
            homeTeam = matcher.group(1); // First capturing group for homeTeam
            awayTeam = matcher.group(2); // Second capturing group for awayTeam
        }

        // Log the extracted teams for debugging
        System.out.println("Extracted Home Team: " + homeTeam);
        System.out.println("Extracted Away Team: " + awayTeam);


        // Return the teams as an array
        return Optional.ofNullable(TeamResponse.builder()
                .homeTeam(homeTeam)
                .awayTeam(awayTeam)
                .build());
    }

    public static String preprocessJson(String jsonString) {
        // Remove any unwanted newlines or extra spaces
        jsonString = jsonString.replaceAll("\\s*\"\\s*\\n\\s*", "\"")
                .replaceAll("\\s*\\n\\s*", " ")
                .replaceAll("\\s*\"\\s*,\\s*\"", "\",\""); // Fix commas if needed
        return jsonString;
    }

}
