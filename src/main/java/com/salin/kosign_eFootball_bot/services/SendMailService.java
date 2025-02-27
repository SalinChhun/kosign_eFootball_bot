package com.salin.kosign_eFootball_bot.services;


import com.salin.kosign_eFootball_bot.payload.pincode.PinCodeRequest;

public interface SendMailService {

    public void sendMail(PinCodeRequest pinCodeRequest);
}
