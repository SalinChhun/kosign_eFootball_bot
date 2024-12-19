package com.salin.kosign_eFootball_bot.component;

import com.salin.kosign_eFootball_bot.services.log.WeSoccerLoggingService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomRequestBodyAdvice extends RequestBodyAdviceAdapter {

    private final WeSoccerLoggingService weSoccerLoggingService;
    private final HttpServletRequest httpServletRequest;

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    @NonNull
    public Object afterBodyRead(@NonNull Object body,
                                @NonNull HttpInputMessage inputMessage,
                                @NonNull MethodParameter parameter,
                                @NonNull Type targetType,
                                @NonNull Class<? extends HttpMessageConverter<?>> converterType) {

        if (httpServletRequest.getRequestURI().contains("/api")) {
            weSoccerLoggingService.logRequest(httpServletRequest, body);
        }

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
}
