package com.joebrooks.mapshotimageapi.map.task;

import lombok.Getter;

@Getter
public enum CompanyType {
    KAKAO("kakao"),
    GOOGLE("google");

    private String type;

    private CompanyType(String type){
        this.type = type;
    }
}
