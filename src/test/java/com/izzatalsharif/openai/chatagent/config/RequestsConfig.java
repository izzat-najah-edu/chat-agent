package com.izzatalsharif.openai.chatagent.config;

import com.izzatalsharif.openai.chatagent.util.FileUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@Configuration
@Import(FileUtility.class)
public class RequestsConfig {

    @Autowired
    private FileUtility fileUtility;

    /**
     * @return A simple invalid request that chat completion rejects.
     */
    @Bean
    public String badRequest() {
        return "{...}";
    }

    /**
     * @return A simple valid request that chat completion accepts.
     */
    @Bean
    public String validRequest() throws IOException {
        return fileUtility.readFile("agent/simpleRequest.json");
    }

    /**
     * This is a template for an agent that formats a string of colors & places into JSON format.
     *
     * @return The test agent template.
     */
    @Bean
    public String testAgentRequest() throws IOException {
        return fileUtility.readFile("agent/testAgent.json");
    }

}
