package com.salin.kosign_eFootball_bot;

import com.salin.kosign_eFootball_bot.config.InMemoryMultipartFile;
import com.salin.kosign_eFootball_bot.payload.MatchResultResponse;
import com.salin.kosign_eFootball_bot.services.GeminiRestService;
import com.salin.kosign_eFootball_bot.services.MatchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

//    @Autowired
//    private PhotoRepository repository;

    private final GeminiRestService geminiAIService;

    private final MatchResultService matchResultService;

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
            String caption = update.getMessage().getCaption();

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
                if (caption != null) {
                    var teamResponse = geminiAIService.predictImage(multipartFile);
                    if (teamResponse.isPresent()) {
                        var isSaveMatchResult = matchResultService.createMatchResult(teamResponse.get());

                        if (isSaveMatchResult && caption.equalsIgnoreCase("insert")) {
                            sendMessage(chatId, teamResponse.get());
                        } else {
                            sendErrorMessage(chatId, "error");
                        }

                    }
                } else {
                    sendErrorMessage(chatId, "insert");
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


    private void sendMessage(String chatId, MatchResultResponse matchResultResponse) {
        StringBuilder message = new StringBuilder();

        // Match Result Header
        message.append("<b>🏆 Match Result 🏆</b>\n\n");

        // Scoreline
        message.append("<b>📊 Score:</b>\n");
        message.append("<b></b>\n");
        message.append(String.format(
                "<b>%s</b> <b>%s</b> — <b>%s</b> <b>%s</b>\n\n",
                matchResultResponse.getHomeTeam(),
                matchResultResponse.getHomeScore(),
                matchResultResponse.getAwayScore(),
                matchResultResponse.getAwayTeam()
        ));

        // Match Statistics Header
        message.append("<b>📈 Match Statistics:</b>\n");

        // Table with spacing
        message.append("<b></b>\n");
        message.append(String.format(
                "<b>%s</b>     <b>Possession</b>     <b>%s</b>\n",
                matchResultResponse.getHomePossession(),
                matchResultResponse.getAwayPossession()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Shots</b>     <b>%s</b>\n",
                matchResultResponse.getHomeShots(),
                matchResultResponse.getAwayShots()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Fouls</b>     <b>%s</b>\n",
                matchResultResponse.getHomeFouls(),
                matchResultResponse.getAwayFouls()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Offsides</b>     <b>%s</b>\n",
                matchResultResponse.getHomeOffsides(),
                matchResultResponse.getAwayOffsides()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Corner Kicks</b>     <b>%s</b>\n",
                matchResultResponse.getHomeCornerKicks(),
                matchResultResponse.getAwayCornerKicks()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Free Kicks</b>     <b>%s</b>\n",
                matchResultResponse.getHomeFreeKicks(),
                matchResultResponse.getAwayFreeKicks()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Passes</b>     <b>%s</b>\n",
                matchResultResponse.getHomePasses(),
                matchResultResponse.getAwayPasses()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Successful Passes</b>     <b>%s</b>\n",
                matchResultResponse.getHomeSuccessfulPasses(),
                matchResultResponse.getAwaySuccessfulPasses()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Crosses</b>     <b>%s</b>\n",
                matchResultResponse.getHomeCrosses(),
                matchResultResponse.getAwayCrosses()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Interceptions</b>     <b>%s</b>\n",
                matchResultResponse.getHomeInterceptions(),
                matchResultResponse.getAwayInterceptions()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Tackles</b>     <b>%s</b>\n",
                matchResultResponse.getHomeTackles(),
                matchResultResponse.getAwayTackles()
        ));

        message.append(String.format(
                "<b>%s</b>     <b>Saves</b>     <b>%s</b>\n",
                matchResultResponse.getHomeSaves(),
                matchResultResponse.getAwaySaves()
        ));

        // Send the message
        try {
            SendMessage sendMessage = new SendMessage(chatId, message.toString());
            sendMessage.setParseMode("HTML");
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    private void sendErrorMessage(String chatId, String type) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (type.equalsIgnoreCase("insert"))
            sendMessage.setText("\uD83D\uDCE2 Send images with the caption 'insert' to save the match result.");
        else
            sendMessage.setText("Something went wrong while trying to save the match result.");

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
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

