package com.joebrooks.mapshotimageapi.presentation;

import com.joebrooks.mapshotimageapi.map.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/kakao")
public class KakaoMapController {

    private final MapService mapService;

    @GetMapping
    public ResponseEntity<byte[]> isAvailable(){

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(mapService.getImage());
    }
}
