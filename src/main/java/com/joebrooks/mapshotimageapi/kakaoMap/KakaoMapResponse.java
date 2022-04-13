package com.joebrooks.mapshotimageapi.kakaoMap;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KakaoMapResponse {

    @JsonProperty("left_count")
    private int leftCount;

    @JsonProperty("is_my_turn")
    private boolean isMyTurn;

    @JsonProperty("image_complete")
    private boolean imageComplete;

    @JsonProperty("image_data")
    private byte[] imageData;

}
