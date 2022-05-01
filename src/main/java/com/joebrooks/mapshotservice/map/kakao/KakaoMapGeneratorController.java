package com.joebrooks.mapshotservice.map.kakao;

import com.joebrooks.mapshotservice.map.task.UserMapRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map/gen/kakao")
public class KakaoMapGeneratorController {

    @GetMapping
    public String getKakaoMap(@ModelAttribute UserMapRequest mapRequest, Model model){
        model.addAttribute("mapRequest", mapRequest);

        return "map/kakao";
    }
}