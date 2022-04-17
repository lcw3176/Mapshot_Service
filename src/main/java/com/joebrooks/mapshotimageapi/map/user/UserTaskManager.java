package com.joebrooks.mapshotimageapi.map.user;

import com.joebrooks.mapshotimageapi.driver.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.Queue;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserTaskManager {

    private final Queue<UserMapRequest> taskQueue = new LinkedList<>();
    private final DriverService driverService;
    private final ApplicationEventPublisher publisher;


    public void addTask(UserMapRequest request) {
        taskQueue.add(request);
        log.info("애드 : {}", taskQueue.size());
    }

    public int getWaiterCount(){
        return taskQueue.size();
    }

    public void removeSessionIfPresent(WebSocketSession session){
        for(UserMapRequest user : taskQueue){
            if(user.getSession().equals(session)){
                taskQueue.remove(user);
                break;
            }
        }
    }


    public void run() {
        try{
            log.info("큐 인출");
            UserMapRequest request = taskQueue.poll();
            publisher.publishEvent(UserMapResponse.builder()
                    .imageComplete(true)
                    .imageData(driverService.capturePage(request.getUri()))
                    .build());

            log.info("퍼블리시");
        } catch (Exception e){
            log.error("getImage 에러", e);
        }

    }

}
