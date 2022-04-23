package com.joebrooks.mapshotimageapi.global.sns;

import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;
import com.joebrooks.mapshotimageapi.global.httpClient.HttpClient;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.stereotype.Component;

@Component
public class SlackClient extends HttpClient {

    private final String slackUrl = System.getenv("SLACK_URL");

    public void sendMessage(ExceptionResponse errors) {
        String message = SlackMessageFormatter.makeErrorMessage(errors);
        post(slackUrl, message);
    }

    public void sendMessage(String name, Exception exception) {
        int len = Math.min(ExceptionUtils.getStackTrace(exception).length(), 1700);

        ExceptionResponse errorMessage = ExceptionResponse.builder()
                .name(name)
                .message(ExceptionUtils.getStackTrace(exception).substring(0, len))
                .build();

        sendMessage(errorMessage);
    }


}