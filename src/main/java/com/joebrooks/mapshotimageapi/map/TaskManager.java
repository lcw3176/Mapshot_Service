package com.joebrooks.mapshotimageapi.map;

import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.map.websocket.UserMapRequest;
import com.joebrooks.mapshotimageapi.map.websocket.UserMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.LinkedList;
import java.util.Queue;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskManager {

    private final DriverService driverService;
    private static final Queue<UserMapRequest> requestQueue = new LinkedList<>();
    private final ApplicationEventPublisher publisher;
    private final SlackClient slackClient;


    public void addRequest(UserMapRequest request){
        requestQueue.add(request);
    }

    public void removeRequest(WebSocketSession session){
        for(UserMapRequest request : requestQueue){
            if(request.getSession().equals(session)){
                requestQueue.remove(request);
                break;
            }
        }
    }


    @Async
    public void execute(){

        if(requestQueue.size() >= 1) {
            UserMapRequest request = requestQueue.poll();
            try {
                publisher.publishEvent(UserMapResponse.builder()
                        .done(true)
                        .imageData(driverService.capturePage(request.getUri()))
                        .index(0)
                        .session(request.getSession())
                        .build());
            } catch (Exception e) {
                log.error("지도 캡쳐 에러", e);
                slackClient.sendMessage("지도 캡쳐 에러", e);
                publisher.publishEvent(UserMapResponse.builder()
                        .done(true)
                        .imageData(null)
                        .index(0)
                        .session(request.getSession())
                        .build());
            }
        }
    }
}
