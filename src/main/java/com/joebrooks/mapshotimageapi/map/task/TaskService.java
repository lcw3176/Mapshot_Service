package com.joebrooks.mapshotimageapi.map.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final LinkedList<WebSocketSession> sessionList = new LinkedList<>();
    private final DriverService driverService;
    private final SlackClient slackClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public void addSession(WebSocketSession session){
        sessionList.add(session);
    }

    public void removeSession(WebSocketSession session){
        sessionList.remove(session);
    }

    public void sendWaitersCountToUser(WebSocketSession session) {
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

    public void sendLeftCountToWaiters() {
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


    @Async
    public CompletableFuture<UserMapResponse> execute(UserMapRequest request){
        if(!request.getSession().isOpen()){
            sessionList.remove(request.getSession());
            return CompletableFuture.completedFuture(null);
        }

        UserMapResponse response;

        try {
            response = UserMapResponse.builder()
                    .done(true)
                    .imageData(driverService.capturePage(request.getUri()))
                    .index(0)
                    .build();


        } catch (Exception e) {
            log.error("지도 캡쳐 에러", e);
            slackClient.sendMessage("지도 캡쳐 에러", e);

            response = UserMapResponse.builder()
                    .done(true)
                    .imageData(null)
                    .index(0)
                    .build();
        }

        return CompletableFuture.completedFuture(response);
    }
}
