package com.joebrooks.mapshotimageapi.global.exception;

import com.joebrooks.mapshotimageapi.admin.login.AdminLoginException;
import com.joebrooks.mapshotimageapi.global.sns.IMessageClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionAdvice {

    private final IMessageClient iMessageClient;


    @ExceptionHandler(AdminLoginException.class)
    public void adminLoginException(AdminLoginException adminLoginException){
        log.info("\n adminLoginException" +
                 "\n id: {}" +
                 "\n pw: {}",
                adminLoginException.getAdminRequest().getNickName(),
                adminLoginException.getAdminRequest().getPassword());

        sendErrorMessage(adminLoginException);
    }

    @ExceptionHandler(IllegalStateException.class)
    public void illegalStateException(IllegalStateException illegalStateException){
        log.info("\nIllegalStateException" +
                 "\n{}", illegalStateException.getMessage());

        sendErrorMessage(illegalStateException);
    }

    private void sendErrorMessage(Exception e){
        int len = Math.min(ExceptionUtils.getStackTrace(e).length(), 1700);

        ExceptionResponse errorMessage = ExceptionResponse.builder()
                .name(e.getClass().toString())
                .message(ExceptionUtils.getStackTrace(e).substring(0, len))
                .build();

        iMessageClient.sendMessage(errorMessage);
    }
}
