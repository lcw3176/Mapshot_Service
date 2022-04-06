package com.joebrooks.mapshotimageapi.driver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChromeDriverConfig {

    @Bean
    public ChromeOptions chromeOptions(){
        String chromeDriverPath = "CHROMEDRIVER_PATH";

        System.setProperty("webdriver.chrome.driver", System.getenv(chromeDriverPath));
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");

        return options;
    }

    @Bean(destroyMethod = "quit")
    public ChromeDriverExtends chromeDriver() throws Exception {
        return new ChromeDriverExtends(chromeOptions());
    }
}
