package com.joebrooks.mapshotimageapi.map.user;

import com.joebrooks.mapshotimageapi.kakaoMap.KakaoMapHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final KakaoMapHandler kakaoMapHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(kakaoMapHandler, "/map/gen")
                .setAllowedOrigins("*")
                .withSockJS();
    }
}
