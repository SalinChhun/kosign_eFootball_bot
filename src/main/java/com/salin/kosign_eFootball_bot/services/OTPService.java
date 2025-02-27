package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.payload.pincode.PinCodeRequest;

public interface OTPService {

    PinCodeRequest generatePinCode(String email);
    String confirmPinCode(String email, String pinCode);

}
