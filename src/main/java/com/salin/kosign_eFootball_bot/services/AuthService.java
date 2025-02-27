package com.salin.kosign_eFootball_bot.services;

import com.salin.kosign_eFootball_bot.domain.User;
import com.salin.kosign_eFootball_bot.payload.auth.LoginRequest;
import com.salin.kosign_eFootball_bot.payload.auth.LoginResponse;
import com.salin.kosign_eFootball_bot.payload.auth.RegisterRequest;
import com.salin.kosign_eFootball_bot.payload.auth.ResetPasswordRequest;
import com.salin.kosign_eFootball_bot.payload.user.ChangePasswordRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public interface AuthService {

    public LoginResponse register(RegisterRequest request);

    public LoginResponse login(LoginRequest request);

    public LoginResponse thirdPartyLogin(LoginRequest request);

    void saveUserToken(User user, String jwtToken);

    void revokeAllUserTokens(User user);

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser);

    void resetPassword(ResetPasswordRequest resetPasswordRequest);

}
