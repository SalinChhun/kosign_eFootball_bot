package com.salin.kosign_eFootball_bot.security.config;

import com.salin.kosign_eFootball_bot.api.ApiResponse;
import com.salin.kosign_eFootball_bot.api.ApiStatus;
import com.salin.kosign_eFootball_bot.api.EmptyJsonResponse;
import com.salin.kosign_eFootball_bot.api.StatusCode;
import com.salin.kosign_eFootball_bot.utils.ObjectUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class UnauthorizedHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        try (ServletServerHttpResponse res = new ServletServerHttpResponse(response)) {
            res.setStatusCode(HttpStatus.UNAUTHORIZED);
            res.getServletResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            ApiStatus apiStatus = new ApiStatus(StatusCode.UNAUTHORIZED);
            ApiResponse<Object> apiResponse = new ApiResponse<>(apiStatus, new EmptyJsonResponse());

            res.getBody().write(ObjectUtils.writeValueAsString(apiResponse).getBytes());
        }
    }
}
