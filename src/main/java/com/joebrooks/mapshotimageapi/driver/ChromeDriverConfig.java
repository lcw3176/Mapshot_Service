package com.joebrooks.mapshotimageapi.driver;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ChromeDriverConfig {

    @Bean
    public ChromeOptions chromeOptions(){
        String chromeDriverPath = "CHROMEDRIVER_PATH";
        String chromeBinaryPath = "CHROME_BINARY";

        ChromeOptions options = new ChromeOptions();

        if(System.getProperty("os.name").toLowerCase().contains("win")){
            System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        } else {
            System.setProperty("webdriver.chrome.driver", System.getenv(chromeDriverPath));
            options.setBinary(System.getenv(chromeBinaryPath));
        }

        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-gpu");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--incognito");

        return options;
    }

    @Bean(destroyMethod = "quit")
    public ChromeDriverExtends chromeDriverExtends() throws Exception {
        return new ChromeDriverExtends(chromeOptions());
    }

    @Bean
    public FluentWait<ChromeDriverExtends> webDriverWait() throws Exception {

        return new FluentWait<ChromeDriverExtends>(chromeDriverExtends())
                .withTimeout(Duration.ofSeconds(60L))
                .pollingEvery(Duration.ofSeconds(3L))
                .ignoring(NoSuchElementException.class);
    }
}
