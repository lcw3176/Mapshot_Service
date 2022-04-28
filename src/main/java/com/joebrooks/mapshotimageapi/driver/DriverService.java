package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService{

    private final ChromeDriverExtends chromeDriverExtends;
    private final WebDriverWait webDriverWait;


    public String capturePage(UriComponents uri){
        chromeDriverExtends.get(uri.toString());
        webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));

        return chromeDriverExtends.getFullScreenshotAsBase64();
    }

}
