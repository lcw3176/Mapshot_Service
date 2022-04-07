package com.joebrooks.mapshotimageapi.driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChromeDriverConfig {

    @Bean
    public ChromeOptions chromeOptions(){
        String chromeDriverPath = "CHROMEDRIVER_PATH";
        String chromeBinaryPath = "CHROME_BINARY";

        System.setProperty("webdriver.chrome.driver", System.getenv(chromeDriverPath));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.setBinary(System.getenv(chromeBinaryPath));

        return options;
    }

    @Bean(destroyMethod = "quit")
    public ChromeDriverExtends chromeDriver() throws Exception {
        return new ChromeDriverExtends(chromeOptions());
    }

    @Bean
    public WebDriverWait webDriverWait() throws Exception {
        long timeOutSeconds = 30;

        return new WebDriverWait(chromeDriver(), timeOutSeconds);
    }
}
