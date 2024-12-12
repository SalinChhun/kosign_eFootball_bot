package com.salin.kosign_eFootball_bot.config;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration(proxyBeanMethods = false)
public class GeminiConfiguration {

    @Bean
    public VertexAI vertexAI() throws IOException {
        return new VertexAI("kosignefootballbot", "us-central1");
    }

    @Bean
    public GenerativeModel generativeModel(VertexAI vertexAI) {
        return new GenerativeModel("gemini-1.5-flash-001", vertexAI);
    }

}
