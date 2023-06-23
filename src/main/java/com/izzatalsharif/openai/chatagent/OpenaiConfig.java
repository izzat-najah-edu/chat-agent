package com.izzatalsharif.openai.chatagent;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
class OpenaiConfig {

    private static final String CHAT_COMPLETION_URL
            = "https://api.openai.com/v1/chat/completions";

    private static final String OPENAI_API_KEY
            = System.getenv("OPENAI_API_KEY");

    @Bean
    @Qualifier("chatCompletion")
    public WebClient chatCompletionWebClient() {
        return WebClient.builder()
                .baseUrl(CHAT_COMPLETION_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + OPENAI_API_KEY)
                .build();
    }

}
