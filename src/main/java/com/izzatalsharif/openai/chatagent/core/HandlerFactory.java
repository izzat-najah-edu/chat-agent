package com.izzatalsharif.openai.chatagent.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.izzatalsharif.openai.chatagent.handler.JsonInputFormatter;
import com.izzatalsharif.openai.chatagent.handler.JsonOutputParser;
import com.izzatalsharif.openai.chatagent.handler.XmlInputFormatter;
import com.izzatalsharif.openai.chatagent.handler.XmlOutputParser;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class HandlerFactory {

    @Nullable
    private ObjectMapper objectMapper;

    @Nullable
    private XmlMapper xmlMapper;

    public ObjectMapper objectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
        }
        return objectMapper;
    }

    public XmlMapper xmlMapper() {
        if (xmlMapper == null) {
            xmlMapper = new XmlMapper();
        }
        return xmlMapper;
    }

    public <I> JsonInputFormatter<I> jsonFormatter() {
        return new JsonInputFormatter<>(objectMapper());
    }

    public <I> XmlInputFormatter<I> xmlFormatter() {
        return new XmlInputFormatter<>(xmlMapper());
    }

    public <O> JsonOutputParser<O> jsonParser(Class<O> clazz) {
        return new JsonOutputParser<>(objectMapper(), clazz);
    }

    public <O> XmlOutputParser<O> xmlParser(Class<O> clazz) {
        return new XmlOutputParser<>(xmlMapper(), clazz);
    }

}
