package com.joebrooks.mapshotimageapi.admin;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminRequest{
    private String nickName;
    private String password;
}