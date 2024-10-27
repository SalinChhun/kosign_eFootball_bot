package com.salin.kosign_eFootball_bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
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

    @Value("${telegram-setting.username}")
    private String telegramBotUsername;

    @Value("${telegram-setting.access-token}")
    private String telegramAccessToken;

    @Override
    public void onUpdateReceived(Update update) {
        String chatId = update.getMessage().getChatId().toString();
        String cmd = update.getMessage().getText();


        if (update.hasMessage() && update.getMessage().hasPhoto()) {
//            String chatId = update.getMessage().getChatId().toString();

            // Get the list of photos (the largest photo is usually the last one)
            List<PhotoSize> photos = update.getMessage().getPhoto();
            PhotoSize photo = photos.get(photos.size() - 1); // Get the largest photo

            // Get the file ID of the photo
            String fileId = photo.getFileId();

            // You can now download the file using the file ID
            try {
                // First, get the file object
                File file = execute(new GetFile(fileId));
                String filePath = file.getFilePath();

                // Construct the URL to download the file
                String fileUrl = "https://api.telegram.org/file/bot" + telegramAccessToken + "/" + filePath;
                System.err.println("fileUrl"+ fileUrl);
                // Now you can download the image from the URL
                // Use your preferred method to download the image (e.g., HttpURLConnection, Apache HttpClient, etc.)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//        System.err.println("cmd "+ cmd);
//        var p = repository.findByCmd(cmd);
//        if (p!=null){
//            sendImageFromUrl(chatId, p.getImageUrl());
//        }else
        if(cmd.equals("/confess")){
            SendMessage message=new SendMessage();
            message.setText("Do it now!!");
            message.setChatId(chatId);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
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
