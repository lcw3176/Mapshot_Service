package com.joebrooks.mapshotimageapi.global.httpClient;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public abstract class HttpClient {

    private WebClient getClient(String baseUrl) {
        int timeoutMillis = 3000;

        reactor.netty.http.client.HttpClient httpClient = reactor.netty.http.client.HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutMillis)
                .responseTimeout(Duration.ofMillis(timeoutMillis))
                .doOnConnected( conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(timeoutMillis, TimeUnit.MILLISECONDS))  //sec
                                .addHandlerLast(new WriteTimeoutHandler(timeoutMillis, TimeUnit.MILLISECONDS)) //sec
                );

        return WebClient.builder()
                .baseUrl(baseUrl)
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }

    protected void get(String path){
        try {
            getClient(path).get()
                    .acceptCharset(StandardCharsets.UTF_8)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("failed get")));

        } catch (Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }


    public void post(String path, Object body){
        try{
            getClient(path).post()
                    .acceptCharset(StandardCharsets.UTF_8)
                    .bodyValue(body)
                    .retrieve()
                    .onStatus(HttpStatus::isError, response -> Mono.error(new RuntimeException("failed post")));

        } catch(Exception e){
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}