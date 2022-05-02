package com.joebrooks.mapshotservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MapshotServiceApplication {

    public static final String PROFILE_LOCATIONS = "spring.config.location="
            + "classpath:application.yml,"
            + "real-application.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(MapshotServiceApplication.class)
                .properties(PROFILE_LOCATIONS)
                .run(args);
    }

}
