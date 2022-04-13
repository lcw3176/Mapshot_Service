package com.joebrooks.mapshotimageapi.kakaoMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class KakaoMapHandler extends TextWebSocketHandler {

    private static final LinkedList<WebSocketSession> waitList = new LinkedList<>();

    private final KakaoMapService kakaoMapService;
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        waitList.add(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        if(kakaoMapService.isAvailable() && waitList.size() >= 1){
            WebSocketSession currentUser = waitList.get(0);

            if(currentUser.equals(session)){
                try{
                    KakaoMapResponse waitEndResponse = KakaoMapResponse.builder()
                            .isMyTurn(true)
                            .build();

                    session.sendMessage(new TextMessage(mapper.writeValueAsString(waitEndResponse))); // 처리 중 메세지 보내주기

                    KakaoMapRequest request = mapper.readValue(message.getPayload(), KakaoMapRequest.class);
                    byte[] data = kakaoMapService.getImage(request.getUri());

                    KakaoMapResponse imageResponse = KakaoMapResponse.builder()
                            .imageComplete(true)
                            .imageData(data)
                            .build();

                    currentUser.sendMessage(new TextMessage(mapper.writeValueAsString(imageResponse))); // 이미지 데이터 보내주기

                    return;
                } catch (Exception e){
                    log.info("카카오 맵 예외");
                    log.info(e.getMessage());
                } finally {
                    waitList.remove(session);
                }

            }
        }

        KakaoMapResponse response = KakaoMapResponse.builder()
                                        .isMyTurn(false)
                                        .leftCount(waitList.indexOf(session) + 1)
                                        .build();

        session.sendMessage(new TextMessage(mapper.writeValueAsString(response))); // 대기 인원 수 보내주기
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        waitList.remove(session);
    }
}
