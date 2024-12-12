package com.salin.kosign_eFootball_bot.controller;

import com.salin.kosign_eFootball_bot.payload.TeamResponse;
import com.salin.kosign_eFootball_bot.services.GeminiAIService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GeminiAIController extends KosignEfootballBotResController{


    private final GeminiAIService geminiAIService;

    @Autowired
    public GeminiAIController(GeminiAIService geminiAIService) {
        this.geminiAIService = geminiAIService;
    }

    @PostMapping("/generate")
    public Optional<TeamResponse> generateContent(@RequestParam("file") MultipartFile file) {
        return geminiAIService.predictImage(file);

    }
}
