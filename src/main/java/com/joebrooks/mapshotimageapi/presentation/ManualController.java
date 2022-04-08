package com.joebrooks.mapshotimageapi.presentation;

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

        return "redirect:/manual/1";
    }

    @GetMapping("/{page}")
    public String showManual(Model model, @PathVariable(value = "page", required = false) Optional<Integer> page){
        int manualNum = page.orElse(1);

        model.addAttribute("manual", ManualType.values());
        model.addAttribute("pageNumber", manualNum);

        return "manual";
    }

     private enum ManualType {
         One("좌표 탐색"),
         Two("반경 설정"),
         Three("지도 종류"),
         Four("출력 타입"),
         Five("부가 기능"),
         Six("수집 시작");

        private final String info;

        private ManualType(String info){
            this.info = info;
        }

        public String getInfo(){
            return this.info;
        }
    }
}


