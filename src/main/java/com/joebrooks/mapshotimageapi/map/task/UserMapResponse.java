package com.joebrooks.mapshotimageapi.map.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMapResponse {

    @JsonProperty("index")
    private int index;

    @JsonProperty("done")
    private boolean done;

    @JsonProperty("imageData")
    private String imageData;

}