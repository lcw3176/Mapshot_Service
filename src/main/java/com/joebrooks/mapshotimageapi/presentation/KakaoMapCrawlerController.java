package com.joebrooks.mapshotimageapi.presentation;

import com.joebrooks.mapshotimageapi.map.MapRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/map/kakao/crawl")
public class KakaoMapCrawlerController {

    @GetMapping
    public String getKakaoMap(@ModelAttribute MapRequest kakaoMap, Model model){
        model.addAttribute("kakaoMapInfo", kakaoMap);

        return "map/kakao";
    }
}