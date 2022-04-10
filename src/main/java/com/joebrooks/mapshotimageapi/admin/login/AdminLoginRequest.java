package com.joebrooks.mapshotimageapi.admin.login;

import lombok.*;

@Data
@Builder
public class AdminLoginRequest {
    private String nickName;
    private String password;
}