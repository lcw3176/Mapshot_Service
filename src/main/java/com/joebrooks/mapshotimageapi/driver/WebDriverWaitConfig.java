package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WebDriverWaitConfig {

    private final FirefoxDriver firefoxDriver;

    @Bean
    public WebDriverWait webDriverWait(){
        long timeOutSeconds = 30;

        return new WebDriverWait(firefoxDriver, timeOutSeconds);
    }
}
