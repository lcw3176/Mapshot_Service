package com.joebrooks.mapshotservice.manual;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.PositiveOrZero;

@Controller
@Validated
@RequestMapping("/manual")
public class ManualController {

    @GetMapping
    public String setPageAndRedirect(){

        return "redirect:/manual/0";
    }

    @GetMapping("/{page}")
    public String showManual(Model model, @PositiveOrZero @PathVariable(value = "page", required = false) Integer page){
        if(page >= ManualType.values().length){
            throw new IllegalArgumentException("잘못된 매뉴얼 접근");
        }

        model.addAttribute("manual", ManualType.values());
        model.addAttribute("pageNumber", page);

        return "fragment/manual/manual";
    }
}


