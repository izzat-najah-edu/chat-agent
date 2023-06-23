package com.izzatalsharif.openai.chatagent.exception;

public class OpenaiException extends AgentException {

    public OpenaiException(String message) {
        super(message);
    }

    public OpenaiException(String message, Throwable cause) {
        super(message, cause);
    }

}
