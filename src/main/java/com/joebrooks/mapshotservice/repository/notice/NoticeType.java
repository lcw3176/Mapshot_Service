package com.joebrooks.mapshotservice.repository.notice;

import lombok.Getter;

@Getter
public enum NoticeType {
    UPDATE("업데이트"),
    FIX("오류수정"),
    RESERVED_CHECK("점검예정");

    private String title;

    private NoticeType(String title){
        this.title = title;
    }
}
