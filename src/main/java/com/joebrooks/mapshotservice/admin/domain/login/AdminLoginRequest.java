package com.joebrooks.mapshotservice.admin.domain.login;

import lombok.*;

@Data
@Builder
public class AdminLoginRequest {
    private String nickName;
    private String password;
}