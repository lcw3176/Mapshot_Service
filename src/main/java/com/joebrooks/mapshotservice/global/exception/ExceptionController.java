package com.joebrooks.mapshotservice.global.exception;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ExceptionController extends BasicErrorController {

    public ExceptionController(ErrorAttributes errorAttributes,
                               ServerProperties serverProperties,
                               List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }

    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {

        HttpStatus httpStatus = getStatus(request);
        ModelAndView modelAndView = new ModelAndView("error/errorPage");
        modelAndView.addObject("code", httpStatus.value());

        return modelAndView;
    }
}
