package com.joebrooks.mapshotimageapi.map.task;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTaskManagerTest {


//    @MockBean
//    private DriverService driverService;
//
//    @MockBean
//    private UserSocketHandler userSocketHandler;
//
//    @Autowired
//    private UserTaskManager userTaskManager;
//
//    @Test
//    public void 큐_감시_테스트() throws IOException {
//        given(driverService.capturePage(any(UriComponents.class)))
//                .willReturn(new byte[10]);
//
//        userTaskManager.addTask(UserMapRequest.builder()
//                .companyType(CompanyType.KAKAO)
//                .lat(111)
//                .lng(33)
//                .layerMode(false)
//                .level(2)
//                .type(null)
//                .session(null)
//                .build());
//
//        await().during(Duration.ofSeconds(10));
//        verify(userSocketHandler, times(1)).afterMapGenerationCompleted(any(UserMapResponse.class));
//
//    }
}