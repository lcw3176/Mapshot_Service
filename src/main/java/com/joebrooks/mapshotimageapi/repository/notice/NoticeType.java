package com.joebrooks.mapshotimageapi.repository.notice;

import lombok.Getter;

@Getter
public enum NoticeType {
    UPDATE("업데이트"),
    FIX("오류 수정"),
    RESERVED_CHECK("점검 예정");

    private String title;

    private NoticeType(String title){
        this.title = title;
    }
}