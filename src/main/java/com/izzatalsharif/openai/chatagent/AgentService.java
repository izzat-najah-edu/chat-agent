package com.izzatalsharif.openai.chatagent;

import com.izzatalsharif.openai.chatagent.exception.InputFormattingException;
import com.izzatalsharif.openai.chatagent.exception.OutputParsingException;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

/**
 * The AgentService class is a key component in the interaction with the OpenAI API.
 * It is responsible for sending requests to the API and processing the responses.
 *
 * <p>Each AgentService instance is associated with a specific template, input handler, and output parser.
 * The template is a JSON string that outlines the structure of the request to be sent to the OpenAI API.
 * Refer the OpenAI API documentation for how to formulate this JSON template.
 *
 * <p>The input handler and output parser are strategies to determine how the input and output data will be formatted.
 * They are designed to accommodate any formatting required by the prompt. The specific prompt used determines the appropriate data handler.
 * This flexibility allows for a wide range of prompts and response formats to be used with the OpenAI API.
 *
 * <p>For example a prompt might ask ChatCompletion to accept XML and return MarkDown.
 * In such case you would need an XML input handler, and a MarkDown output parser.
 *
 * <p>The requestAndParse method is the core method in this class. It accepts an input object of type I,
 * uses the data handler to convert it to a string, and injects it into the request template. The resulting string is then sent as a request to the API.
 *
 * <p>The APIs response is a string. This string is processed by the data handler to convert it to an object of the output class O
 * and returned by the requestAndParse method.
 *
 * <p>If an error occurs during the conversion, an OpenaiException is thrown. This can occur due to the inherent unpredictability of the OpenAI APIs responses.
 * Even if the logic of the application is sound, the API may return a response that cannot be successfully processed by the data formatters.
 *
 * <p>The request template must contain a placeholder for the prompt that will be included in the request.
 * The placeholder is specified in the template as "{prompt}".
 *
 * <p>For example, a template (usually stored in a .json file) might look like this:
 * <pre>
 * {
 *   "model": "gpt-3.5-turbo",
 *   "messages": [
 *     {
 *       "role": "system",
 *       "content": "You are a helpful assistant."
 *     },
 *     {
 *       "role": "user",
 *       "content": "{prompt}"
 *     }
 *   ]
 * }
 * </pre>
 *
 * @param <I> the type of the input to the AgentService
 * @param <O> the type of the output from the AgentService
 */
@Log
public class AgentService<I, O> {

    /**
     * The template string used for the OpenAI API request. This string should contain a "{prompt}" placeholder
     * that will be replaced with the actual prompt data.
     */
    private final String template;

    /**
     * The InputFormatter used to format the input data.
     */
    private final InputFormatter<I> formatter;

    /**
     * The OutputParser used to parse the output data.
     */
    private final OutputParser<O> parser;

    /**
     * The OpenaiService used to send requests to the OpenAI API.
     */
    private final OpenaiService openaiService;

    /**
     * Constructs a new AgentService with the given template, handler, parser, and OpenaiService.
     *
     * @param template      the template string for the OpenAI API request
     * @param formatter     the InputFormatter for formatting input data
     * @param parser        the OutputParser for parsing output data
     * @param openaiService the OpenaiService for sending requests to the OpenAI API
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    AgentService(String template,
                 InputFormatter<I> formatter,
                 OutputParser<O> parser,
                 OpenaiService openaiService)
            throws IllegalArgumentException {
        if (!template.contains("{prompt}")) {
            throw new IllegalArgumentException("template does not contain a prompt placeholder");
        }
        this.template = template;
        this.formatter = formatter;
        this.parser = parser;
        this.openaiService = openaiService;
    }

    /**
     * Sends a request to the OpenAI API with the given input data, and returns the parsed output data.
     *
     * @param input the input data
     * @return Mono of the parsed output data
     * @throws InputFormattingException if an error occurs during input data formatting
     * @throws OutputParsingException   if an error occurs during output data parsing
     */
    public Mono<O> requestAndParse(I input) throws InputFormattingException, OutputParsingException {
        return request(input)
                .map(Response::getContent)
                .map(parser::parseOutput);
    }

    /**
     * Sends a request to the OpenAI API with the given input data, and returns the raw Response.
     *
     * @param input the input data
     * @return the Mono Response from the OpenAI API
     * @throws InputFormattingException if an error occurs during input data formatting
     * @throws OutputParsingException   if an error occurs during output data parsing
     */
    public Mono<Response> request(I input) throws InputFormattingException, OutputParsingException {
        var prompt = formatter.formatInput(input);
        var body = injectRequest(prompt);
        return openaiService.chatCompletion(body)
                // logger
                .map(response -> {
                    log.info(response.toString());
                    return response;
                });
    }

    /**
     * Injects a prompt into the request template.
     *
     * @param prompt the prompt to be injected into the request template
     * @return the request template with the prompt injected
     */
    private String injectRequest(String prompt) {
        return template.replace("{prompt}", prompt);
    }

}
