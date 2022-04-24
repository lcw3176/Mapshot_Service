package com.joebrooks.mapshotimageapi.global.exception;

import com.joebrooks.mapshotimageapi.admin.login.AdminLoginException;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionAdvice {


    private final SlackClient slackClient;

    @ExceptionHandler(AdminLoginException.class)
    public void adminLoginException(AdminLoginException adminLoginException){
        log.info("\n adminLoginException" +
                 "\n id: {}" +
                 "\n pw: {}",
                adminLoginException.getAdminRequest().getNickName(),
                adminLoginException.getAdminRequest().getPassword());

        slackClient.sendMessage(adminLoginException);
    }

    @ExceptionHandler(Exception.class)
    public void illegalStateException(Exception exception){
        log.error(exception.getMessage(), exception);
        slackClient.sendMessage(exception);
    }

}
