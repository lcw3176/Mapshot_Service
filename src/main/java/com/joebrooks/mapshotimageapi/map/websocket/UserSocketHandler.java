package com.joebrooks.mapshotimageapi.map.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.map.task.UserMapRequest;
import com.joebrooks.mapshotimageapi.map.task.UserMapResponse;
import com.joebrooks.mapshotimageapi.map.task.UserTaskManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.LinkedList;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSocketHandler extends TextWebSocketHandler {

    private final LinkedList<WebSocketSession> sessionList = new LinkedList<>();
    private final UserTaskManager userTaskManager;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        UserMapRequest request = mapper.readValue(message.getPayload(), UserMapRequest.class);
        request.setSession(session);
//        userTaskManager.addTask(request);


        UserMapResponse response = UserMapResponse.builder()
                .index(sessionList.size() - 1)
                .build();

        session.sendMessage(new TextMessage(mapper.writeValueAsString(response)));
    }

    @EventListener
    public void afterMapGenerationCompleted(UserMapResponse response) throws IOException {
        response.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(response)));
        sessionList.remove(response.getSession());

        for(int i = 0; i < sessionList.size(); i++){
            UserMapResponse refreshedResponse = UserMapResponse.builder()
                    .index(sessionList.size() - 1)
                    .build();

            sessionList.get(i).sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        userTaskManager.removeSessionIfPresent(session);
        sessionList.remove(session);
    }
}
