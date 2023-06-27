package com.izzatalsharif.openai.chatagent.core;

import com.izzatalsharif.openai.chatagent.InputFormatter;
import com.izzatalsharif.openai.chatagent.OutputParser;
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

    private final OpenaiService openaiService;

    private final HandlerFactory handlerFactory;

    /**
     * Creates a new AgentService with the specified template and data handlers.
     *
     * @param template  the template to be used by the AgentService.
     *                  It must be a valid JSON string.
     * @param formatter the InputFormatter to be used by the AgentService
     * @param parser    the OutputParser to be used by the AgentService
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createAgentService(String template, InputFormatter<I> formatter, OutputParser<O> parser) {
        return new AgentService<>(template, formatter, parser, openaiService);
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
        return createAgentService(template, input -> input, output -> output);
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
        return createAgentService(template, handlerFactory.jsonFormatter(), parser);
    }

    /**
     * Creates a new AgentService with the specified template and an XML InputFormatter.
     *
     * @param template the template to be used by the AgentService.
     *                 It must be a valid JSON string.
     * @param parser   the OutputParser to be used by the AgentService
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createXmlFormatterAgentService(String template, OutputParser<O> parser) {
        return createAgentService(template, handlerFactory.xmlFormatter(), parser);
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
        return createAgentService(template, formatter, handlerFactory.jsonParser(outputClass));
    }

    /**
     * Creates a new AgentService with the specified template and an XML OutputParser.
     *
     * @param template    the template to be used by the AgentService.
     *                    It must be a valid JSON string.
     * @param formatter   the InputFormatter to be used by the AgentService
     * @param outputClass the class of the output object
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createXmlParserAgentService(String template, InputFormatter<I> formatter, Class<O> outputClass) {
        return createAgentService(template, formatter, handlerFactory.xmlParser(outputClass));
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
        return createAgentService(template, handlerFactory.jsonFormatter(), handlerFactory.jsonParser(outputClass));
    }

    /**
     * Creates a new AgentService with the specified template and both an XML InputFormatter and OutputParser.
     *
     * @param template    the template to be used by the AgentService.
     *                    It must be a valid JSON string.
     * @param outputClass the class of the output object
     * @return a new AgentService instance
     * @throws IllegalArgumentException if the template does not contain a prompt placeholder
     */
    public <I, O> AgentService<I, O> createXmlAgentService(String template, Class<O> outputClass) {
        return createAgentService(template, handlerFactory.xmlFormatter(), handlerFactory.xmlParser(outputClass));
    }

}
