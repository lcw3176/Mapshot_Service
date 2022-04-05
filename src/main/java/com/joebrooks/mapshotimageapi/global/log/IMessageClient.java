package com.joebrooks.mapshotimageapi.global.log;

import com.joebrooks.mapshotimageapi.global.exception.ExceptionResponse;

public interface IMessageClient {
    void sendMessage(ExceptionResponse response);
}
