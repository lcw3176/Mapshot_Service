package com.joebrooks.mapshotimageapi.global.sns;

import com.joebrooks.mapshotimageapi.global.httpClient.HttpClient;
import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;
import org.springframework.stereotype.Component;


@Component
public class SlackClient extends HttpClient implements IMessageClient{

    private final String slackUrl = System.getenv("SLACK_URL");

    @Override
    public void sendMessage(ExceptionResponse errors) {
        String message = SlackMessageFormatter.makeErrorMessage(errors);
        post(slackUrl, message);
    }


}