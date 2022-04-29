package com.joebrooks.mapshotimageapi.map.task;

import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.global.sns.SlackClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.LinkedList;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final LinkedList<SseEmitter> sseList = new LinkedList<>();
    private final DriverService driverService;
    private final SlackClient slackClient;

    public SseEmitter addUser(){
        SseEmitter sseEmitter = new SseEmitter(60000L);
        sseEmitter.onCompletion(sseList::remove);
        sseEmitter.onTimeout(sseList::remove);

        sseList.add(sseEmitter);

        return sseEmitter;
    }

    @Async
    public void execute(UserMapRequest request, SseEmitter sseEmitter) throws IOException {
        if(!sseList.contains(sseEmitter)){
            return;
        }

        UserMapResponse response = null;

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
        } finally {
            sseEmitter.send(response, MediaType.APPLICATION_JSON);
            sseEmitter.complete();
        }

    }
}
