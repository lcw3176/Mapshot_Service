package com.joebrooks.mapshotimageapi.driver;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FirefoxDriverConfig {

    @Bean(destroyMethod = "quit")
    public FirefoxDriver firefoxDriver(){
        String driverPath = System.getenv("GECKODRIVER_PATH");
        String binary = System.getenv("FIREFOX_BIN");

        System.setProperty("webdriver.gecko.driver", driverPath);
        File pathToBinary = new File(binary);

        FirefoxBinary firefoxBinary = new FirefoxBinary(pathToBinary);
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary(firefoxBinary);
        options.setHeadless(true);

        return new FirefoxDriver(options);
    }
}
