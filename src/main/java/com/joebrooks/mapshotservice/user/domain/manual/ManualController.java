package com.joebrooks.mapshotservice.user.domain.manual;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Validated
@RequestMapping("/manual")
public class ManualController {

    @GetMapping
    public String showManual(Model model){
        model.addAttribute("manual", ManualType.values());

        return "fragment/manual/manual";
    }
}


