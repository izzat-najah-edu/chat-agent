package com.izzatalsharif.openai.chatagent.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;

@RequiredArgsConstructor
@Component
public class FileUtility {

    private final ResourceLoader resourceLoader;

    public String readFile(String resourcePath) throws IOException {
        var resource = resourceLoader.getResource("classpath:" + resourcePath);
        var path = resource.getFile().toPath();
        return Files.readString(path);
    }

}
