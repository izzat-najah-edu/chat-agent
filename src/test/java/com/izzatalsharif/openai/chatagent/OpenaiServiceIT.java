package com.izzatalsharif.openai.chatagent;

import com.izzatalsharif.openai.chatagent.exception.OpenaiException;
import com.izzatalsharif.openai.chatagent.util.FileUtility;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

/**
 * Integration test suite for the OpenaiService class.
 * The purpose of these tests is to validate that the OpenaiService interacts correctly with the actual OpenAI API,
 * and that it handles different types of responses correctly.
 */
@SpringBootTest
@ContextConfiguration(classes = {OpenaiConfig.class, OpenaiService.class, FileUtility.class})
class OpenaiServiceIT {

    @Autowired
    private OpenaiService openaiService;

    @Autowired
    private FileUtility fileUtility;

    /**
     * This test validates that the OpenaiService can successfully send a request to the OpenAI API and receive a response.
     * The test reads a pre-defined request from a file, sends it to the OpenAI API via the OpenaiService,
     * and asserts that the response is not null.
     */
    @Test
    void chatCompletion_success() throws IOException {
        String requestBody = fileUtility.readFile("example/request.json");
        Response response = openaiService.chatCompletion(requestBody).block();
        assertThat(response).isNotNull();
    }

    /**
     * This test validates that the OpenaiService correctly handles 4xx errors from the OpenAI API.
     * The test sends a bad request to the OpenAI API via the OpenaiService and asserts that an OpenaiException is thrown.
     * This test ensures that the OpenaiService is able to correctly handle and report client errors.
     */
    @Test
    void chatCompletion_4xxError() {
        assertThatExceptionOfType(OpenaiException.class)
                .isThrownBy(() -> {
                    String badRequest = "{...}";
                    openaiService.chatCompletion(badRequest).block();
                });
    }

    /**
     * This test is currently disabled. If enabled, it would validate that the OpenaiService correctly handles 5xx errors from the OpenAI API.
     * These errors are server errors, meaning that they are caused by issues on the APIs side, not by the request sent to the API.
     * The test would send a request to the OpenAI API via the OpenaiService and assert that an OpenaiException is thrown.
     * This test would ensure that the OpenaiService is able to correctly handle and report server errors.
     */
    @Disabled
    @Test
    void chatCompletion_5xxError() {
        // From OpenAI API Documentation:
        // 500 - The server had an error while processing your request
        // 503 - The engine is currently overloaded, please try again later
        assertThatExceptionOfType(OpenaiException.class)
                .isThrownBy(this::chatCompletion_success);
    }
}
