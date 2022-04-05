package com.joebrooks.mapshotimageapi.global.sns;

import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;

public interface IMessageClient {
    void sendMessage(ExceptionResponse response);
}
