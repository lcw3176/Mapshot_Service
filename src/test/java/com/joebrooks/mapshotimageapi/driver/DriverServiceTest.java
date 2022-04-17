package com.joebrooks.mapshotimageapi.driver;

import com.joebrooks.mapshotimageapi.map.CompanyType;
import com.joebrooks.mapshotimageapi.map.user.UserMapResponse;
import com.joebrooks.mapshotimageapi.map.user.UserSocketHandler;
import com.joebrooks.mapshotimageapi.map.user.UserMapRequest;
import com.joebrooks.mapshotimageapi.map.user.UserTaskManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.util.UriComponents;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class DriverServiceTest {

}