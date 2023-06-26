package com.izzatalsharif.openai.chatagent;

import com.izzatalsharif.openai.chatagent.config.TestAgentConfig;
import com.izzatalsharif.openai.chatagent.dto.ColorsAndPlaces;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {
        ChatAgentConfig.class,
        TestAgentConfig.class
})
public class AgentServiceIT {

    @Autowired
    private AgentService<String, ColorsAndPlaces> testAgent;

    @Test
    void test() {

    }

}
