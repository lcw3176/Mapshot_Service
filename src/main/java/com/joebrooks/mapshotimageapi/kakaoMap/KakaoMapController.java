package com.joebrooks.mapshotimageapi.kakaoMap;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/kakao")
public class KakaoMapController {

    private final KakaoMapService mapService;

    @GetMapping
    public ResponseEntity<Boolean> getRequestAvailable() {
        if(mapService.isAvailable()){
            return ResponseEntity.ok().body(true);
        }

        return ResponseEntity.ok().body(false);
    }

    @PostMapping
    public ResponseEntity<byte[]> getFullSizeMapImage(@RequestBody KakaoMapRequest kakaoMapInfo) {

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(mapService.getImage(kakaoMapInfo.getUri()));
    }

}
