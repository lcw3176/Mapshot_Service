package com.joebrooks.mapshotservice.admin.login;

import lombok.Getter;

@Getter
public class AdminLoginException extends RuntimeException {

    private final AdminLoginRequest adminRequest;

    public AdminLoginException(AdminLoginRequest adminRequest){
        super(adminRequest.toString());
        this.adminRequest = adminRequest;

    }
}
