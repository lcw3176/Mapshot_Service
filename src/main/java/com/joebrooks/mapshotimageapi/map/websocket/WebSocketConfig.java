package com.joebrooks.mapshotimageapi.map.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final UserSocketHandler userSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userSocketHandler, "/map/gen")
//                .setAllowedOrigins(
//                        "https://richshrimp.tk",
//                        "https://www.richshrimp.tk")
                .setAllowedOrigins("http://146.56.175.42")
                .withSockJS();
    }
}
