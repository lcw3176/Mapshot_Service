package com.joebrooks.mapshotimageapi.map;

import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import com.joebrooks.mapshotimageapi.map.websocket.UserMapRequest;
import com.joebrooks.mapshotimageapi.map.websocket.UserMapResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskManager {

    private final DriverService driverService;
    private final SlackClient slackClient;


    @Async
    public CompletableFuture<UserMapResponse> execute(UserMapRequest request){
        if(!request.getSession().isOpen()){
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
