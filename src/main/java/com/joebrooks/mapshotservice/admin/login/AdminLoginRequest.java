package com.joebrooks.mapshotservice.admin.login;

import lombok.*;

@Data
@Builder
public class AdminLoginRequest {
    private String nickName;
    private String password;
}