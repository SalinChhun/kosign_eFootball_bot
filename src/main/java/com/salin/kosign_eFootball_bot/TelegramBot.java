package com.salin.kosign_eFootball_bot;

import com.salin.kosign_eFootball_bot.config.InMemoryMultipartFile;
import com.salin.kosign_eFootball_bot.services.GeminiRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

//    @Autowired
//    private PhotoRepository repository;

    @Autowired
    private GeminiRestService geminiAIService;

    @Value("${telegram-setting.username}")
    private String telegramBotUsername;

    @Value("${telegram-setting.access-token}")
    private String telegramAccessToken;

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String cmd = update.getMessage().getText();


        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize photo = photos.get(photos.size() - 1); // Get the largest photo
            String fileId = photo.getFileId();

            try {
                // First, get the file object
                File file = execute(new GetFile(fileId));
                String filePath = file.getFilePath();
                String fileUrl = "https://api.telegram.org/file/bot" + telegramAccessToken + "/" + filePath;

                // Download the image from the URL
                byte[] imageBytes = downloadImage(fileUrl);

                // Create a MultipartFile implementation
                MultipartFile multipartFile = new InMemoryMultipartFile(file.getFilePath(), imageBytes, photo.getFileSize(), file.getFilePath());

                // Process the image with GeminiAIService
                var teamResponse = geminiAIService.predictImage(multipartFile);
                if (teamResponse.isPresent()) {
                    System.err.println("teamResponse "+ teamResponse.get().getAwayTeam());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private byte[] downloadImage(String fileUrl) throws IOException {
        try (InputStream in = new URL(fileUrl).openStream()) {
            return in.readAllBytes();
        }
    }

    private void sendImageFromUrl(String chatId, String urlImage) {

        URL url = null;
        try {
            url = new URL(urlImage);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(inputStream, "image.jpg")); // Filename is optional

        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return telegramBotUsername;
    }

    @Override
    public String getBotToken() {
        return telegramAccessToken;
    }
}

