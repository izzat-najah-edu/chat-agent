package com.izzatalsharif.openai.chatagent.exception;

import com.izzatalsharif.openai.chatagent.OutputParser;

/**
 * The OutputParsingException is a custom exception class that is thrown when an error occurs while parsing the output data.
 *
 * <p>This exception is typically thrown by implementations of the OutputParser interface when they encounter an error
 * while converting the output data received from the OpenAI API into a format that can be used by the application.
 * This typically means that the template used for chat completion didn't explain the output format clearly to OpenAI API.
 *
 * @see OutputParser
 */
public class OutputParsingException extends AgentException {

    public OutputParsingException(String message) {
        super(message);
    }

    public OutputParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}
