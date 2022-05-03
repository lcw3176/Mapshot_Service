package com.joebrooks.mapshotservice.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/opensource")
public class OpensourceController {

    @GetMapping
    public String showOpensource(){

        return "fragment/opensource/opensource";
    }
}