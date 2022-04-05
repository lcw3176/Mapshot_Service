package com.joebrooks.mapshotimageapi.kakaoMap;


import lombok.Data;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class KakaoMapRequest {

    private boolean layerMode;
    private double lat;
    private double lng;
    private int level;
    private String type;


    public UriComponents getUri(){

        return UriComponentsBuilder
                .fromPath("http://localhost:8080/map/gen/kakao")
                .queryParam("layerMode", this.layerMode)
                .queryParam("lat", this.lat)
                .queryParam("lng", this.lng)
                .queryParam("level", this.level)
                .queryParam("type", this.type)
                .build(true);
    }
}