package com.joebrooks.mapshotimageapi.map.task;

import com.joebrooks.mapshotimageapi.driver.DriverService;
import com.joebrooks.mapshotimageapi.map.CompanyType;
import com.joebrooks.mapshotimageapi.map.websocket.UserSocketHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.util.UriComponents;

import java.io.IOException;
import java.time.Duration;

import static org.awaitility.Awaitility.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class UserTaskManagerTest {


    @MockBean
    private DriverService driverService;

    @MockBean
    private UserSocketHandler userSocketHandler;

    @Autowired
    private UserTaskManager userTaskManager;

    @Test
    public void 큐_감시_테스트() throws IOException {
        given(driverService.capturePage(any(UriComponents.class)))
                .willReturn(new byte[10]);

        userTaskManager.addTask(UserMapRequest.builder()
                .companyType(CompanyType.KAKAO)
                .lat(111)
                .lng(33)
                .layerMode(false)
                .level(2)
                .type(null)
                .session(null)
                .build());

        await().during(Duration.ofSeconds(10));
        verify(userSocketHandler, times(1)).afterMapGenerationCompleted(any(UserMapResponse.class));

    }
}