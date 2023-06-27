package com.izzatalsharif.openai.chatagent.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.izzatalsharif.openai.chatagent.OutputParser;
import com.izzatalsharif.openai.chatagent.exception.OutputParsingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XmlOutputParser<T>
        implements OutputParser<T> {

    private final XmlMapper xmlMapper;

    private final Class<T> outputClass;

    @Override
    public T parseOutput(String output) throws OutputParsingException {
        try {
            return xmlMapper.readValue(output, outputClass);
        } catch (JsonProcessingException e) {
            throw new OutputParsingException("openai response couldn't parse from xml", e);
        }
    }

}

