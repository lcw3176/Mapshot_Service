package com.joebrooks.mapshotimageapi.global.exception;

import com.joebrooks.mapshotimageapi.global.sns.IMessageClient;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.NoSuchSessionException;
import org.openqa.selenium.WebDriverException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestControllerExceptionAdvice {

    private final IMessageClient iMessageClient;


    @ExceptionHandler(NoSuchSessionException.class)
    public void clientDisconnectedHandler(){

    }

    @ExceptionHandler({ClientAbortException.class, WebDriverException.class})
    public void serverTimeOutHandler(){

    }

    @ExceptionHandler(Exception.class)
    public void exceptionHandler(Exception e) {
        int len = Math.min(ExceptionUtils.getStackTrace(e).length(), 1700);

        ExceptionResponse errorMessage = ExceptionResponse.builder()
                .name(e.getClass().toString())
                .message(ExceptionUtils.getStackTrace(e).substring(0, len))
                .build();

        sendErrorMessage(errorMessage);
    }

    private void sendErrorMessage(ExceptionResponse error){
        iMessageClient.sendMessage(error);
    }
}