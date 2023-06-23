package com.izzatalsharif.openai.chatagent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.chatagent.handler.JsonInputFormatter;
import com.izzatalsharif.openai.chatagent.handler.JsonOutputParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * The AgentServiceFactory class is responsible for creating instances of the AgentService class, each equipped with the appropriate data handler.
 *
 * <p>Each factory method corresponds to a specific type of data handler:
 *
 * <ul>
 *   <li>The {@code agentService} method creates an AgentService instance that uses a specified InputFormatter and OutputParser.
 *   The choice of formatter and parser depends on the specific requirements of the prompt that the AgentService will be used with.
 *   The prompt determines the format of the input and output, and the appropriate formatter and parser should be chosen accordingly.</li>
 * </ul>
 *
 * <p>This design allows for flexibility and modularity. If a new type of data handler is needed in the future,
 * a new method can be added to this factory to create AgentService instances with that handler.
 *
 * @see AgentService
 * @see InputFormatter
 * @see OutputParser
 */
@RequiredArgsConstructor
@Component
public class AgentServiceFactory {

    private final ObjectMapper objectMapper;

    private final OpenaiService openaiService;

    private <I, O> AgentService<I, O> agentService(String template, InputFormatter<I> formatter, OutputParser<O> parser) {
        return new AgentService<>(template, formatter, parser, openaiService);
    }

    private <I> JsonInputFormatter<I> jsonFormatter() {
        return new JsonInputFormatter<>(objectMapper);
    }

    private <O> JsonOutputParser<O> jsonParser(Class<O> clazz) {
        return new JsonOutputParser<>(objectMapper, clazz);
    }

    /**
     * Creates a new AgentService with the specified template and data handler.
     *
     * @param template  the template to be used by the AgentService.
     *                  It must be a valid JSON string.
     * @param formatter the InputFormatter to be used by the AgentService
     * @param parser    the OutputParser to be used by the AgentService
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createAgentService(String template, InputFormatter<I> formatter, OutputParser<O> parser)
            throws IllegalArgumentException {
        return agentService(template, formatter, parser);
    }

    /**
     * Creates a new AgentService with the specified template and a JSON InputFormatter.
     *
     * @param template the template to be used by the AgentService.
     *                 It must be a valid JSON string.
     * @param parser   the OutputParser to be used by the AgentService
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createJsonFormatterAgentService(String template, OutputParser<O> parser) {
        return agentService(template, jsonFormatter(), parser);
    }

    /**
     * Creates a new AgentService with the specified template and a JSON OutputParser.
     *
     * @param template    the template to be used by the AgentService.
     *                    It must be a valid JSON string.
     * @param formatter   the InputFormatter to be used by the AgentService
     * @param outputClass the class of the output object
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createJsonParserAgentService(String template, InputFormatter<I> formatter, Class<O> outputClass) {
        return agentService(template, formatter, jsonParser(outputClass));
    }

    /**
     * Creates a new AgentService with the specified template and both a JSON InputFormatter and OutputParser.
     *
     * @param template    the template to be used by the AgentService.
     *                    It must be a valid JSON string.
     * @param outputClass the class of the output object
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createJsonAgentService(String template, Class<O> outputClass) {
        return agentService(template, jsonFormatter(), jsonParser(outputClass));
    }

    /**
     * Creates a new AgentService with the specified template and simple string-based InputFormatter and OutputParser.
     * It does not perform any special formatting or parsing.
     *
     * @param template the template to be used by the AgentService.
     *                 It must be a valid JSON string.
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public AgentService<String, String> createSimpleAgentService(String template) {
        return agentService(template, input -> input, output -> output);
    }

}
