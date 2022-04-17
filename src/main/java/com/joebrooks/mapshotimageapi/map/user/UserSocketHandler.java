package com.joebrooks.mapshotimageapi.map.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserSocketHandler extends TextWebSocketHandler {

    private final UserTaskManager userTaskManager;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        UserMapRequest request = mapper.readValue(message.getPayload(), UserMapRequest.class);
        userTaskManager.addTask(request);

//        publisher.publishEvent(mapper.readValue(message.getPayload(), KakaoMapRequest.class));

//        if(kakaoMapService.isAvailable() && waitList.size() >= 1){
//            WebSocketSession currentUser = waitList.get(0);
//
//            if(currentUser.equals(session)){
//                try{
//                    KakaoMapResponse waitEndResponse = KakaoMapResponse.builder()
//                            .isMyTurn(true)
//                            .build();
//
//                    session.sendMessage(new TextMessage(mapper.writeValueAsString(waitEndResponse))); // 처리 중 메세지 보내주기
//
//                    KakaoMapRequest request = mapper.readValue(message.getPayload(), KakaoMapRequest.class);
//                    byte[] data = kakaoMapService.getImage(request.getUri());
//
//                    KakaoMapResponse imageResponse = KakaoMapResponse.builder()
//                            .imageComplete(true)
//                            .imageData(data)
//                            .build();
//
//                    currentUser.sendMessage(new TextMessage(mapper.writeValueAsString(imageResponse))); // 이미지 데이터 보내주기
//
//                    return;
//                } catch (Exception e){
//                    log.error(e.getMessage(), e);
//                } finally {
//                    waitList.remove(session);
//                }
//
//            }
//        }
//
//        KakaoMapResponse response = KakaoMapResponse.builder()
//                                        .isMyTurn(false)
//                                        .leftCount(waitList.indexOf(session) + 1)
//                                        .build();
//
//        session.sendMessage(new TextMessage(mapper.writeValueAsString(response))); // 대기 인원 수 보내주기
    }

    @EventListener
    public void sendMapToUser(UserMapResponse response) throws IOException {
        response.getSession().sendMessage(new TextMessage(mapper.writeValueAsString(response)));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        userTaskManager.removeSessionIfPresent(session);
    }
}
