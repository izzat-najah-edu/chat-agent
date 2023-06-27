package com.izzatalsharif.openai.chatagent.config;

import com.izzatalsharif.openai.chatagent.core.AgentService;
import com.izzatalsharif.openai.chatagent.core.AgentServiceFactory;
import com.izzatalsharif.openai.chatagent.ChatAgentConfig;
import com.izzatalsharif.openai.chatagent.dto.ColorsAndPlaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        ChatAgentConfig.class,
        RequestsConfig.class
})
public class TestAgentConfig {

    @Autowired
    private AgentServiceFactory agentServiceFactory;

    @Autowired
    private String testAgentRequest;

    /**
     * <p>This is the test agent, it takes a string of colors & places.
     * <p>It then requests a JSON string of 'colors' and 'places' arrays:
     * <ol>
     *     <li>It sends the template and expects chat completion to generate a JSON.
     *     <li>If the response is not a valid JSON it generates an OutputParsing Exception.
     *     <li>Finally, it parses the string to ColorsAndPlaces object which we defined.
     * </ol>
     *
     * @return The test agent.
     */
    @Bean
    public AgentService<String, ColorsAndPlaces> testAgent() {
        return agentServiceFactory.createJsonParserAgentService(
                testAgentRequest,
                input -> input, // no formatting, sends the string as is.
                ColorsAndPlaces.class
        );
    }

}
