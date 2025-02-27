package com.salin.kosign_eFootball_bot.payload.auth;

import lombok.Data;

@Data
public class ResetPasswordRequest {
    private String email;
    private String newPassword;
    private String confirmNewPassword;
}
