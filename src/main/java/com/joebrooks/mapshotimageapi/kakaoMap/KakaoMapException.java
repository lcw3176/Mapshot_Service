package com.joebrooks.mapshotimageapi.kakaoMap;

public class KakaoMapException extends RuntimeException{

    public KakaoMapException(){
        super();
    }

    public KakaoMapException(String message, Exception e){
        super(message, e);
    }
}
