package com.joebrooks.mapshotservice.global.exception;

import com.joebrooks.mapshotservice.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionAdvice {

    private final SlackClient slackClient;

    @ExceptionHandler({ConstraintViolationException.class, IllegalStateException.class, MethodArgumentTypeMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView violationExceptionHandler(Exception e){
        log.info(e.getMessage(), e);

        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("code", HttpStatus.BAD_REQUEST.value());

        return modelAndView;
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exceptionHandler(Exception exception){
        log.error(exception.getMessage(), exception);
        slackClient.sendMessage(exception);

        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("code", HttpStatus.INTERNAL_SERVER_ERROR.value());

        return modelAndView;
    }

}
