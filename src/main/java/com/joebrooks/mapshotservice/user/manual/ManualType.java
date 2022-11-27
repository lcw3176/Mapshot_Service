package com.joebrooks.mapshotservice.user.manual;

public enum ManualType {
    ZERO("사용 전"),
    ONE("좌표 탐색"),
    TWO("반경 설정"),
    THREE("지도 종류"),
    FOUR("출력 타입"),
    FIVE("부가 기능"),
    SIX("수집 시작");

    private final String info;

    private ManualType(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
}