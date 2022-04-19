package com.joebrooks.mapshotimageapi.map.task;

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

    @JsonProperty("is_done")
    private boolean isDone;

    @JsonProperty("image_data")
    private byte[] imageData;

}
