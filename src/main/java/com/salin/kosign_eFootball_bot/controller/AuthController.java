package com.salin.kosign_eFootball_bot.controller;

import com.salin.kosign_eFootball_bot.payload.auth.LoginRequest;
import com.salin.kosign_eFootball_bot.payload.auth.LoginResponse;
import com.salin.kosign_eFootball_bot.payload.auth.RegisterRequest;
import com.salin.kosign_eFootball_bot.payload.auth.ResetPasswordRequest;
import com.salin.kosign_eFootball_bot.payload.user.ChangePasswordRequest;
import com.salin.kosign_eFootball_bot.services.AuthService;
import com.salin.kosign_eFootball_bot.services.OTPService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController extends KosignEfootballBotResController {

    private final AuthService authenticationService;
    private final OTPService otpService;

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/third-party/login")
    public ResponseEntity<LoginResponse> thirdPartyAuthenticate(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.thirdPartyLogin(request));
    }

    @PostMapping("/refreshToken")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @PostMapping("/generatePinCode")
    public ResponseEntity<?> generatePinCode(@RequestParam String email){
        return ok(otpService.generatePinCode(email));
    }

    @PostMapping("/confirmPinCode")
    public ResponseEntity<?> confirmPinCode(
            @RequestParam String email,
            @RequestParam String pinCode
    ) {
        return ok(otpService.confirmPinCode(email, pinCode));
    }

    @PatchMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        authenticationService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        authenticationService.resetPassword(resetPasswordRequest);
        return ok();
    }


}
