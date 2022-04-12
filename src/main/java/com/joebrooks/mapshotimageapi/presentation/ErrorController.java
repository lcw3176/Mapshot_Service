package com.joebrooks.mapshotimageapi.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error-page")
public class ErrorController {

    @GetMapping
    public String showErrorForm(){
        return "fragment/error/error-page";
    }
}
