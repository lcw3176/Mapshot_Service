package com.joebrooks.mapshotimageapi.map;

import lombok.RequiredArgsConstructor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MapService {

    private final FirefoxDriver firefoxDriver;

    public byte[] getImage(){
        firefoxDriver.get("https://www.naver.com");

        return firefoxDriver.getFullPageScreenshotAs(OutputType.BYTES);
    }
}
