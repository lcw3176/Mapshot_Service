package com.joebrooks.mapshotimageapi.map.task;

import com.joebrooks.mapshotimageapi.map.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapRequest {

    private boolean layerMode;
    private double lat;
    private double lng;
    private int level;
    private String type;
    private CompanyType companyType;
    private WebSocketSession session;

    public void setSession(WebSocketSession session){
        this.session = session;
    }

    public UriComponents getUri(){

        return UriComponentsBuilder
                .fromPath("http://localhost:8080/map/gen/" + this.companyType.getType())
                .queryParam("layerMode", this.layerMode)
                .queryParam("lat", this.lat)
                .queryParam("lng", this.lng)
                .queryParam("level", this.level)
                .queryParam("type", this.type)
                .build(true);
    }
}
