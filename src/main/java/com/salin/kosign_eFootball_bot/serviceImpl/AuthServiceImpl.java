package com.salin.kosign_eFootball_bot.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salin.kosign_eFootball_bot.common.api.StatusCode;
import com.salin.kosign_eFootball_bot.domain.User;
import com.salin.kosign_eFootball_bot.exception.BusinessException;
import com.salin.kosign_eFootball_bot.payload.auth.LoginRequest;
import com.salin.kosign_eFootball_bot.payload.auth.LoginResponse;
import com.salin.kosign_eFootball_bot.payload.auth.RegisterRequest;
import com.salin.kosign_eFootball_bot.payload.auth.ResetPasswordRequest;
import com.salin.kosign_eFootball_bot.payload.user.ChangePasswordRequest;
import com.salin.kosign_eFootball_bot.repository.UserRepository;
import com.salin.kosign_eFootball_bot.security.config.JwtService;
import com.salin.kosign_eFootball_bot.security.token.Token;
import com.salin.kosign_eFootball_bot.security.token.TokenRepository;
import com.salin.kosign_eFootball_bot.security.token.TokenType;
import com.salin.kosign_eFootball_bot.security.user.Role;
import com.salin.kosign_eFootball_bot.services.AuthService;
import com.salin.kosign_eFootball_bot.utils.EmailUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @Override
    public LoginResponse register(RegisterRequest request) {

        // Check before register
        if (request.getEmail() == null || StringUtils.isBlank(request.getEmail())) {
            throw new BusinessException(StatusCode.EMAIL_IS_NUll);
        } else if (!EmailUtils.isEmail(request.getEmail())) {
            throw new BusinessException(StatusCode.INVALID_EMAIL_FORMAT);
        } else if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException(StatusCode.EMAIL_EXIST);
        }

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException(StatusCode.EMAIL_NOT_FOUND));

        // login validation
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        }  catch (BadCredentialsException ex) {
            // Handle incorrect password
            throw new BusinessException(StatusCode.BAD_CREDENTIALS);
        }
        catch (AuthenticationException ex) {
            // Handle other authentication exceptions
            throw new BusinessException(StatusCode.AUTHENTICATION_FAILED, ex.getMessage());
        }


        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponse thirdPartyLogin(LoginRequest request) {

        var requestUser = userRepository.findByEmail(request.getEmail());

        if (requestUser.isEmpty()) {
            var user = User.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            return LoginResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else {
            return login(request);
        }
    }

    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BusinessException(StatusCode.BAD_CREDENTIALS);
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new BusinessException(StatusCode.VERIFY_PASSWORD);
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest) {

        var user = userRepository.findUserByEmail(resetPasswordRequest.getEmail());

        // check if the two new passwords are the same
        if (!resetPasswordRequest.getNewPassword().equals(resetPasswordRequest.getConfirmNewPassword())) {
            throw new BusinessException(StatusCode.VERIFY_PASSWORD);
        }

        if (user.getIsPinCodeEnable()) {
            // update the password
            user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
            user.setIsPinCodeEnable(false);

            // save the new password
            userRepository.save(user);
        } else {
            throw new BusinessException(StatusCode.PINCODE_REQUIRED);
        }

    }

    @Override
    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    @Override
    public void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    @Override
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                try {
                    var accessToken = jwtService.generateToken(user);
                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);

                    var authResponse = LoginResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();

                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                } catch (ExpiredJwtException e) {
                    // Handle the case where the new access token cannot be generated due to the expiration of the refresh token.
                    // You may want to return an error response to the client.
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Refresh token has expired. Please re-authenticate.");
                }
            } else {
                // Handle the case where the refresh token is not valid (e.g., tampered or expired).
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid refresh token. Please re-authenticate.");
            }
        }
    }

}
