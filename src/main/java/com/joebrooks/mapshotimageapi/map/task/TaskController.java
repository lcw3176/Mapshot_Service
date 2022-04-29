package com.joebrooks.mapshotimageapi.map.task;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/map/gen")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public SseEmitter executeTask(@ModelAttribute UserMapRequest userMapRequest) throws IOException {
        SseEmitter sseEmitter = taskService.addUser();
        taskService.execute(userMapRequest, sseEmitter);
        taskService.sendWaitCountToUser(sseEmitter);

        return sseEmitter;
    }
}
