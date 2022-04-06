package com.joebrooks.mapshotimageapi.kakaoMap;

import com.joebrooks.mapshotimageapi.kakaoMap.KakaoMapRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map/kakao/gen")
public class MapGeneratorController {

    @GetMapping
    public String getKakaoMap(@ModelAttribute KakaoMapRequest mapRequest, Model model){
        model.addAttribute("mapRequest", mapRequest);

        return "map/kakao";
    }
}