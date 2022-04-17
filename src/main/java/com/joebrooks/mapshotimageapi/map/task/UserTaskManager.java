package com.joebrooks.mapshotimageapi.map.task;

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

    private volatile Queue<UserMapRequest> taskQueue = new LinkedList<>();
    private final DriverService driverService;
    private final ApplicationEventPublisher publisher;

    @PostConstruct
    private void init(){
        Thread thread = new Thread(() -> {
            while(true){
                while (taskQueue.size() >= 1){
                    try{
                        UserMapRequest request = taskQueue.poll();
                        publisher.publishEvent(UserMapResponse.builder()
                                        .index(0)
                                        .imageData(driverService.capturePage(request.getUri()))
                                        .build());

                    } catch (Exception e){
                        log.error("지도 캡쳐 에러", e);
                    }

                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void addTask(UserMapRequest request) {
        taskQueue.add(request);
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

}
