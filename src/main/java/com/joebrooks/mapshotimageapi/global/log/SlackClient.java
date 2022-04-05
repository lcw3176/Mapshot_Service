package com.joebrooks.mapshotimageapi.global.log;

import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

@Component
public class SlackClient implements IMessageClient{

    private final String slackUrl = System.getenv("SLACK_URL");

    @Override
    public void sendMessage(ExceptionResponse errors) {
        RestTemplate rt = new RestTemplate();
        String message = SlackMessageFormatter.makeErrorMessage(errors);
        rt.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        rt.postForObject(slackUrl, message, String.class);
    }



}