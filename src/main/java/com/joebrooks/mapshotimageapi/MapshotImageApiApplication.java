package com.joebrooks.mapshotimageapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MapshotImageApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MapshotImageApiApplication.class, args);
    }

}
