package com.salin.kosign_eFootball_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.generativeai.ResponseHandler;

import java.io.IOException;

import java.io.IOException;

@SpringBootApplication
public class TelegramBotApplication {
        public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(TelegramBotApplication.class, args);
        TelegramBot telegramBot = context.getBean(TelegramBot.class);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(telegramBot);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
//    public static void main(String[] args) throws IOException {
//        // TODO(developer): Replace these variables before running the sample.
//        String projectId = "kosignefootballbot";
//        System.err.println("projectId" + projectId);
//        String location = "us-central1";
//        String modelName = "gemini-1.5-flash-001";
//        String textPrompt =
//                "who is ronaldo";
//
//        String output = textInput(projectId, location, modelName, textPrompt);
//        System.out.println(output);
//    }
//
//    // Passes the provided text input to the Gemini model and returns the text-only response.
//    // For the specified textPrompt, the model returns a list of possible store names.
//    public static String textInput(
//            String projectId, String location, String modelName, String textPrompt) throws IOException {
//        // Initialize client that will be used to send requests. This client only needs
//        // to be created once, and can be reused for multiple requests.
//        try (VertexAI vertexAI = new VertexAI(projectId, location)) {
//            GenerativeModel model = new GenerativeModel(modelName, vertexAI);
//
//            GenerateContentResponse response = model.generateContent(textPrompt);
//            String output = ResponseHandler.getText(response);
//            return output;
//        }
//    }

}
