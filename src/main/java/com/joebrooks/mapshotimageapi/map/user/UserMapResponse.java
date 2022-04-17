package com.joebrooks.mapshotimageapi.map.user;

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

    private WebSocketSession session;

    @JsonProperty("left_count")
    private int leftCount;

    @JsonProperty("is_my_turn")
    private boolean isMyTurn;

    @JsonProperty("image_complete")
    private boolean imageComplete;

    @JsonProperty("image_data")
    private byte[] imageData;

}
