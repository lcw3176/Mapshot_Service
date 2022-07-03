package com.joebrooks.mapshotservice.manual;

public enum ManualType {
    Zero("사용 전"),
    One("좌표 탐색"),
    Two("반경 설정"),
    Three("지도 종류"),
    Four("출력 타입"),
    Five("부가 기능"),
    Six("수집 시작");

    private final String info;

    private ManualType(String info){
        this.info = info;
    }

    public String getInfo(){
        return this.info;
    }
}