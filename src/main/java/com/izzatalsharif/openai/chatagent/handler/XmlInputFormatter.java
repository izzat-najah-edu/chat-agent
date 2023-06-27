package com.izzatalsharif.openai.chatagent.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.izzatalsharif.openai.chatagent.InputFormatter;
import com.izzatalsharif.openai.chatagent.exception.InputFormattingException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class XmlInputFormatter<T>
        implements InputFormatter<T> {

    private final XmlMapper xmlMapper;

    @Override
    public String formatInput(T input) throws InputFormattingException {
        try {
            return xmlMapper.writeValueAsString(input);
        } catch (JsonProcessingException e) {
            throw new InputFormattingException("openai request couldn't be formatted to xml", e);
        }
    }

}

