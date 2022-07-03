package com.joebrooks.mapshotservice.manual;

import com.joebrooks.mapshotservice.global.util.DigitValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/manual")
public class ManualController {

    @GetMapping
    public String setPageAndRedirect(){

        return "redirect:/manual/0";
    }

    @GetMapping("/{page}")
    public String showManual(Model model, @PathVariable(value = "page", required = false) Optional<String> page){
        int manualNum = 0;

        if(page.isPresent() && DigitValidator.isDigit(page.get())){
            manualNum = Integer.parseInt(page.get());
        }

        model.addAttribute("manual", ManualType.values());
        model.addAttribute("pageNumber", manualNum);

        return "fragment/manual/manual";
    }
}


