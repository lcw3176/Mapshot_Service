package com.joebrooks.mapshotimageapi.kakaoMap;

import com.joebrooks.mapshotimageapi.driver.ChromeDriverExtends;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;

@Service
@RequiredArgsConstructor
public class KakaoMapService {

    private boolean available = true;
    private final ChromeDriverExtends chromeDriver;
    private final WebDriverWait waiter;

    public byte[] getImage(UriComponents uri){
        try{
            chromeDriver.get(uri.toString());
            waiter.until(ExpectedConditions.presenceOfElementLocated(By.id("checker_true")));

            return chromeDriver.getFullScreenshot();
        } catch (Exception e){
            return null;
        } finally {
            available = true;
        }

    }

    public synchronized boolean isAvailable() {
        if(available){
            available = false;

            return true;
        }

        return false;
    }
}
