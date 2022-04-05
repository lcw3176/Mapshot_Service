package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebDriverWaitConfig {

    private final ChromeDriverExtends chromeDriver;

    @Bean
    public WebDriverWait webDriverWait(){
        long timeOutSeconds = 30;

        return new WebDriverWait(chromeDriver, timeOutSeconds);
    }
}
