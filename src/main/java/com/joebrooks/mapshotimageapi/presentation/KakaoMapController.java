package com.joebrooks.mapshotimageapi.presentation;

import com.joebrooks.mapshotimageapi.map.MapRequest;
import com.joebrooks.mapshotimageapi.map.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/kakao")
public class KakaoMapController {

    private final MapService mapService;

//    @GetMapping
//    public ResponseEntity<Boolean> getRequestAvailable() {
//
////        if(captureService.isAvailable()){
////            return ResponseEntity.ok().body(true);
////        }
////
////        return ResponseEntity.ok().body(false);
//
//        return ResponseEntity.ok(true);
//    }
//
//    @PostMapping
//    public ResponseEntity<byte[]> getFullSizeMapImage(@RequestBody MapRequest kakaoMapInfo) {
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(mapService.getImage(kakaoMapInfo.getUrl()));
//    }

    @GetMapping
    public ResponseEntity<byte[]> test(){
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(mapService.getImage("hello"));
    }
}
