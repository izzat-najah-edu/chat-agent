package com.izzatalsharif.openai.chatagent.core;

import com.izzatalsharif.openai.chatagent.exception.OpenaiException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class OpenaiConfig {

    @Value("${openai.api.chat-completion-url:https://api.openai.com/v1/chat/completions}")
    private String chatCompletionUrl;

    @Value("${openai.api.key:}")
    private String openaiApiKey;

    @PostConstruct
    public void checkProperties() {
        if (openaiApiKey.isEmpty()) {
            throw new OpenaiException("The 'openai.api.key' property must be defined");
        }
    }

    @Bean
    WebClient chatCompletionWebClient() {
        return WebClient.builder()
                .baseUrl(chatCompletionUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .build();
    }

}
