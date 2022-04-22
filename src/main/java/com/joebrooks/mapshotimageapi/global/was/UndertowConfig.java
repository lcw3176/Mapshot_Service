package com.joebrooks.mapshotimageapi.global.was;

import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {

    @Bean
    public UndertowServletWebServerFactory embeddedServletContainerFactory() {
        //        UndertowServletWebServerFactory factory =
        //                new UndertowServletWebServerFactory();
        //        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
        //            @Override
        //            public void customize(io.undertow.Undertow.Builder builder) {
        //                builder.addHttpListener(8080, "0.0.0.0");
        //            }
        //        });
        //
        return new UndertowServletWebServerFactory();
    }
}
