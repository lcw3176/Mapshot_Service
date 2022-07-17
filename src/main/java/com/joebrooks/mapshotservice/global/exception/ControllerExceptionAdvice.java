package com.joebrooks.mapshotservice.global.exception;

import com.joebrooks.mapshotservice.admin.login.AdminLoginException;
import com.joebrooks.mapshotservice.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionAdvice {

    private final SlackClient slackClient;

    @ExceptionHandler(AdminLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void adminLoginException(AdminLoginException adminLoginException){
        log.info("\n adminLoginException" +
                 "\n id: {}" +
                 "\n pw: {}",
                adminLoginException.getAdminRequest().getNickName(),
                adminLoginException.getAdminRequest().getPassword());

        slackClient.sendMessage(adminLoginException);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void violationExceptionHandler(ConstraintViolationException e){
        log.info(e.getMessage(), e);
//        slackClient.sendMessage(e);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void illegalStateExceptionHandler(IllegalStateException e){
        log.info(e.getMessage(), e);
//        slackClient.sendMessage(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void exceptionHandler(Exception exception){
        log.error(exception.getMessage(), exception);
        slackClient.sendMessage(exception);
    }

}
