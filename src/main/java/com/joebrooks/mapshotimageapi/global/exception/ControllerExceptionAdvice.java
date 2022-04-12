package com.joebrooks.mapshotimageapi.global.exception;

import com.joebrooks.mapshotimageapi.admin.login.AdminLoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.groovy.control.messages.ExceptionMessage;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletResponse;

@ControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ControllerExceptionAdvice {

    @ExceptionHandler(AdminLoginException.class)
    public ModelAndView adminLoginException(ServletResponse response, AdminLoginException adminLoginException){
        log.info("\n adminLoginException" +
                 "\n id: {}" +
                 "\n pw: {}",
                adminLoginException.getAdminRequest().getNickName(),
                adminLoginException.getAdminRequest().getPassword());

        return new ModelAndView("redirect:/error-page");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView illegalStateException(IllegalStateException illegalStateException){
        log.info("\nIllegalStateException" +
                 "\n{}", illegalStateException.getMessage());


        return new ModelAndView("redirect:/error-page");
    }
}
