package com.joebrooks.mapshotimageapi.driver;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v99.network.Network;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ChromeDriverConfigTest {

    @Autowired
    private ChromeDriverExtends chromeDriverExtends;
    private static final Logger log = LoggerFactory.getLogger(ChromeDriverConfigTest.class);

    @Test
    public void devtool_캐시_저장_안하기(){
        DevTools devTools = chromeDriverExtends.getDevTools();
        devTools.createSession();

        devTools.send(
                Network.enable(
                        Optional.empty(),
                        Optional.empty(),
                        Optional.of(100000000)));
        devTools.send(Network.setCacheDisabled(true));
        devTools.addListener(Network.responseReceived(),
                responseReceived -> {
                    assertEquals(false, responseReceived.getResponse().getFromDiskCache().get());
                });
        
        chromeDriverExtends.get("https://www.google.com");
        chromeDriverExtends.get("https://www.naver.com");
        chromeDriverExtends.get("https://www.daum.net");
    }
}