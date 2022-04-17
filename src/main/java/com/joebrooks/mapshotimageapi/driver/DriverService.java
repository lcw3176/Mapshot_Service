package com.joebrooks.mapshotimageapi.driver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
@RequiredArgsConstructor
@Slf4j
public class DriverService{

    private final ChromeDriverExtends chromeDriver;
    private final WebDriverWait waiter;


    public byte[] capturePage(UriComponents uri){
        chromeDriver.get(uri.toString());
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));

        return chromeDriver.getFullScreenshot();
    }

}
