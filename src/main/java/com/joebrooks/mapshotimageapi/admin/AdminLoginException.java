package com.joebrooks.mapshotimageapi.admin;

import lombok.Getter;

@Getter
public class AdminLoginException extends RuntimeException {

    private final String id;
    private final String pw;

    public AdminLoginException(String id, String pw){
        this.id = id;
        this.pw = pw;
    }
}
