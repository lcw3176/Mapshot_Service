package com.joebrooks.mapshotimageapi.map.websocket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.socket.WebSocketSession;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapResponse {

    @JsonIgnore
    private WebSocketSession session;

    @JsonProperty("index")
    private int index;

    @JsonProperty("done")
    private boolean done;

    @JsonProperty("imageData")
    private String imageData;

}
