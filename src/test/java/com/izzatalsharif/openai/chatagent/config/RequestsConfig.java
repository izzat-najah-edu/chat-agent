package com.izzatalsharif.openai.chatagent.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Configuration
public class RequestsConfig {

    @Autowired
    private final ResourceLoader resourceLoader;

    private String readFile(String resourcePath) throws IOException {
        var resource = resourceLoader.getResource("classpath:" + resourcePath);
        var path = resource.getFile().toPath();
        return Files.readString(path);
    }

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
        return readFile("agent/simpleRequest.json");
    }

    /**
     * This is a template for an agent that formats a string of colors & places into JSON format.
     *
     * @return The test agent template.
     */
    @Bean
    public String testAgentRequest() throws IOException {
        return readFile("agent/testAgent.json");
    }

}
