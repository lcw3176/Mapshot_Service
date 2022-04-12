package com.joebrooks.mapshotimageapi.kakaoMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.LinkedList;
import java.util.Queue;

@Component
public class KakaoMapHandler extends TextWebSocketHandler {

    private static Queue<WebSocketSession> waitQueue = new LinkedList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        waitQueue.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        waitQueue.remove(session);
    }
}
