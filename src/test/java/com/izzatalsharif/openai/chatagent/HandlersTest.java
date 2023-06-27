package com.izzatalsharif.openai.chatagent;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.izzatalsharif.openai.chatagent.core.HandlerFactory;
import com.izzatalsharif.openai.chatagent.exception.OutputParsingException;
import com.izzatalsharif.openai.chatagent.handler.JsonInputFormatter;
import com.izzatalsharif.openai.chatagent.handler.JsonOutputParser;
import com.izzatalsharif.openai.chatagent.handler.XmlInputFormatter;
import com.izzatalsharif.openai.chatagent.handler.XmlOutputParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ContextConfiguration(classes = HandlerFactory.class)
public class HandlersTest {

    @Autowired
    private HandlerFactory handlerFactory;

    private final SomeObject someObject = new SomeObject(
            List.of("a", "b", "c"),
            List.of("A", "B", "C")
    );

    record SomeObject(
            List<String> a,
            List<String> b
    ) {
    }

    record DifferentObject(
            Integer c,
            Integer d
    ) {
    }

    void assertFormatAndParse(InputFormatter<SomeObject> formatter, OutputParser<SomeObject> parser) {
        var input = someObject;
        String data = formatter.formatInput(input);
        var output = parser.parseOutput(data);
        assertThat(output).isNotNull();
        assertThat(output).isInstanceOf(SomeObject.class);
        assertThat(output).isEqualTo(input);
    }

    void assertParseError(InputFormatter<SomeObject> formatter, OutputParser<DifferentObject> parser) {
        String data = formatter.formatInput(someObject);
        assertThatExceptionOfType(OutputParsingException.class)
                .isThrownBy(() -> parser.parseOutput(data));
    }

    @Test
    void formatThenParseJson_success() {
        assertFormatAndParse(
                handlerFactory.jsonFormatter(),
                handlerFactory.jsonParser(SomeObject.class)
        );
    }

    @Test
    void formatThenParseXml_success() {
        assertFormatAndParse(
                handlerFactory.xmlFormatter(),
                handlerFactory.xmlParser(SomeObject.class)
        );
    }

    @Test
    void formatThenParseJson_parseError() {
        assertParseError(
                handlerFactory.jsonFormatter(),
                handlerFactory.jsonParser(DifferentObject.class)
        );
    }

    @Test
    void formatThenParseXml_parseError() {
        assertParseError(
                handlerFactory.xmlFormatter(),
                handlerFactory.xmlParser(DifferentObject.class)
        );
    }

}
