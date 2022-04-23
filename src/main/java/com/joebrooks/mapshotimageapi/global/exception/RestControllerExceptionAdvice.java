package com.joebrooks.mapshotimageapi.global.exception;

import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class RestControllerExceptionAdvice {

    private final SlackClient slackClient;

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e) {
        int len = Math.min(ExceptionUtils.getStackTrace(e).length(), 1700);

        ExceptionResponse errorMessage = ExceptionResponse.builder()
                .name(e.getMessage())
                .message(ExceptionUtils.getStackTrace(e).substring(0, len))
                .build();

        slackClient.sendMessage(errorMessage);
        log.error(e.getMessage(), e);
    }
}