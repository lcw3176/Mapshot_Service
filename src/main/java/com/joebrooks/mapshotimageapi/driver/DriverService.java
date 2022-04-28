package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService{

    private final ChromeDriverExtends chromeDriverExtends;
    private final FluentWait<ChromeDriverExtends> webDriverWait;


    public String capturePage(UriComponents uri){
        chromeDriverExtends.get(uri.toString());
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));

        return chromeDriverExtends.getFullScreenshotAsBase64();
    }

}
