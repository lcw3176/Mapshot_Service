package com.joebrooks.mapshotimageapi.map;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

    private final FirefoxDriver firefoxDriver;
    private final WebDriverWait waiter;

    public byte[] getImage(String url){
        firefoxDriver.get("https://www.naver.com");
        waiter.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));

        return firefoxDriver.getFullPageScreenshotAs(OutputType.BYTES);
    }
}
