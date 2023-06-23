package com.izzatalsharif.openai.chatagent;

import com.izzatalsharif.openai.chatagent.exception.OutputParsingException;

/**
 * The OutputParser interface defines the contract for classes that handle the parsing of data received from the OpenAI API.
 *
 * <p>The type parameter O represents the output type.
 * The output is the data that will be received from the API.
 *
 * <p>Implementations of this interface are responsible for converting the data received from the API into a format
 * that can be used by the application.
 *
 * <p>The format should be determined by the prompt of the chat completion.
 *
 * @param <T> the type of the output data
 */
@FunctionalInterface
public interface OutputParser<T> {

    /**
     * Parses the output data received from the OpenAI API into an object of type O.
     *
     * @param output the output data as a string
     * @return an object of type O representing the output data
     * @throws OutputParsingException if an error occurs during parsing
     */
    T parseOutput(String output) throws OutputParsingException;

}
