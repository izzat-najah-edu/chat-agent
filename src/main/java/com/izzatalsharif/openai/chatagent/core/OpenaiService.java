package com.izzatalsharif.openai.chatagent.core;

import com.izzatalsharif.openai.chatagent.Response;
import com.izzatalsharif.openai.chatagent.exception.OpenaiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Log
@RequiredArgsConstructor
@Service
public class OpenaiService {

    private final WebClient chatCompletionWebClient;

    @Value("${openai.api.request-timeout:5m}")
    private Duration timeout;

    public Mono<Response> chatCompletion(String requestBody) {
        log.info(requestBody);
        return chatCompletionWebClient.post()
                .body(BodyInserters.fromValue(requestBody))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new OpenaiException(
                                        "4xx error: " + response.statusCode() + " " + errorBody))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(errorBody -> Mono.error(new OpenaiException(
                                        "5xx error: " + response.statusCode() + " " + errorBody))))
                .bodyToMono(Response.class)
                .timeout(timeout, Mono.error(new OpenaiException("Request to OpenAI API timed out.")));
    }

}
