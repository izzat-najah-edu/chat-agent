package com.izzatalsharif.openai.chatagent.exception;

import com.izzatalsharif.openai.chatagent.InputFormatter;

/**
 * The InputFormattingException is a custom exception class that is thrown when an error occurs while formatting the input data.
 *
 * <p>This exception is typically thrown by implementations of the InputFormatter interface when they encounter an error
 * while converting the input data into a format that can be sent to the OpenAI API.
 *
 * @see InputFormatter
 */
public class InputFormattingException extends AgentException {

    public InputFormattingException(String message) {
        super(message);
    }

    public InputFormattingException(String message, Throwable cause) {
        super(message, cause);
    }

}
