package com.izzatalsharif.openai.chatagent;

import com.izzatalsharif.openai.chatagent.exception.InputFormattingException;

/**
 * The InputFormatter interface defines the contract for classes that handle the formatting of data sent to the OpenAI API.
 *
 * <p>The type parameter `I` represents the input type.
 * The input is the data that will be sent to the API.
 *
 * <p>Implementations of this interface are responsible for converting the input data into a format that can be sent to the API.
 * The format should be determined by the prompt of the chat completion.
 *
 * @param <T> the type of the input data
 */
@FunctionalInterface
public interface InputFormatter<T> {

    /**
     * Formats the input data into a string that can be sent to the OpenAI API.
     *
     * @param input the input data
     * @return a string representation of the input data
     * @throws InputFormattingException if an error occurs during formatting
     */
    String formatInput(T input) throws InputFormattingException;

}
