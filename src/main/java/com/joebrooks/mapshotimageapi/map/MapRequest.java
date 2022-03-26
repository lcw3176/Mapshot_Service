package com.joebrooks.mapshotimageapi.map;


import lombok.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class MapRequest {

    private boolean layerMode;
    private double lat;
    private double lng;
    private int level;
    private String type;


    public String getUrl(){
        UriComponents uri = UriComponentsBuilder
                .fromPath("https://mapshot-image-api.herokuapp.com/map/kakao/crawl")
                .queryParam("layerMode", this.layerMode)
                .queryParam("lat", this.lat)
                .queryParam("lng", this.lng)
                .queryParam("level", this.level)
                .queryParam("type", this.type)
                .build(true);

        return uri.toString();
    }
}