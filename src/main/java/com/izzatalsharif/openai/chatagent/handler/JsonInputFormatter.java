package com.izzatalsharif.openai.chatagent.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.chatagent.InputFormatter;
import com.izzatalsharif.openai.chatagent.exception.InputFormattingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonInputFormatter<T>
        implements InputFormatter<T> {

    private final ObjectMapper objectMapper;

    @Override
    public String formatInput(T input) throws InputFormattingException {
        try {
            var json = objectMapper.writeValueAsString(input);
            return quoteFix(json);
        } catch (JsonProcessingException e) {
            throw new InputFormattingException("openai request couldn't be formatted to json", e);
        }
    }

    /**
     * Replaces all double quotes with single quotes.
     * This ensures all JSON parsers can parse it correctly.
     *
     * @param json the JSON string to be fixed
     * @return the fixed JSON string
     */
    private String quoteFix(String json) {
        return json.replaceAll("\"", "'");
    }

}
