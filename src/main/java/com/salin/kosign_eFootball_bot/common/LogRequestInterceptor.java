package com.salin.kosign_eFootball_bot.common;

import com.salin.kosign_eFootball_bot.services.log.WeSoccerLoggingService;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class LogRequestInterceptor implements HandlerInterceptor {

    private final WeSoccerLoggingService weSoccerLoggingService;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {
        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name())
                && request.getMethod().equals(HttpMethod.GET.name())) {
            if (request.getRequestURI().contains("/api/v1")) {
                weSoccerLoggingService.logRequest(request, null);
            }
        }
        return true;
    }
}