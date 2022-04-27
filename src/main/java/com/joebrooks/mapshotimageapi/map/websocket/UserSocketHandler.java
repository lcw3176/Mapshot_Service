package com.joebrooks.mapshotimageapi.map.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.map.TaskManager;
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
    private final TaskManager taskManager;
    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        UserMapRequest request;

        try{
            request = mapper.readValue(message.getPayload(), UserMapRequest.class);
            request.setSession(session);
        } catch (JsonProcessingException e){
            log.error("유효하지 않은 지도 포맷", e);
            slackClient.sendMessage("유효하지 않은 지도 포맷", e);
            return;
        }

        sendWaitCountToUser(session);
        taskManager.addRequest(request);
        taskManager.execute();
    }

    @EventListener
    public void sendMapImage(UserMapResponse response){
        if(response.getSession().isOpen()){
            try {
                response.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(response)));
            } catch (IOException e) {
                log.error("지도 전송 실패", e);
                slackClient.sendMessage("지도 전송 실패", e);
            } finally {
                sendWaitCountToLeftUsers();
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session);
        taskManager.removeRequest(session);
        sendWaitCountToLeftUsers();
    }

    private void sendWaitCountToUser(WebSocketSession session) {
        UserMapResponse refreshedResponse = UserMapResponse.builder()
                .index(sessionList.indexOf(session))
                .done(false)
                .build();

        try{
            session.sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
        } catch (IOException e){
            log.error("대기열 알람 전송 에러", e);
            slackClient.sendMessage("대기열 알람 전송 에러", e);
        }

    }


    private void sendWaitCountToLeftUsers() {
        for(int i = 0; i < sessionList.size(); i++){
            UserMapResponse refreshedResponse = UserMapResponse.builder()
                    .index(sessionList.indexOf(sessionList.get(i)))
                    .done(false)
                    .build();

            try{
                sessionList.get(i).sendMessage(new TextMessage(mapper.writeValueAsString(refreshedResponse)));
            } catch (IOException e){
                log.error("대기열 알람 전송 에러", e);
                slackClient.sendMessage("대기열 알람 전송 에러", e);
            }

        }
    }
}
