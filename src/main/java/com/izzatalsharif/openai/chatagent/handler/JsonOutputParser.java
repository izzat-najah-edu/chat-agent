package com.izzatalsharif.openai.chatagent.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.izzatalsharif.openai.chatagent.OutputParser;
import com.izzatalsharif.openai.chatagent.exception.OutputParsingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class JsonOutputParser<T>
        implements OutputParser<T> {

    private final ObjectMapper objectMapper;

    private final Class<T> outputClass;

    @Override
    public T parseOutput(String output) throws OutputParsingException {
        try {
            return objectMapper.readValue(output, outputClass);
        } catch (JsonProcessingException e) {
            log.info("openai output: " + output);
            throw new OutputParsingException("openai response couldn't parse from json");
        }
    }

}
