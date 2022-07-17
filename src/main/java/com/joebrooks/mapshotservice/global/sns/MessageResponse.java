package com.joebrooks.mapshotservice.global.sns;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponse {
    private String title;
    private String message;
}
