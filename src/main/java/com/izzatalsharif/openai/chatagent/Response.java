package com.izzatalsharif.openai.chatagent;

import java.util.List;

public record Response(
        String id,
        String object,
        String model,
        Usage usage,
        List<Choice> choices
) {

    public record Usage(
            Integer prompt_tokens,
            Integer completion_tokens,
            Integer total_tokens
    ) {
    }

    public record Choice(
            Message message,
            String finish_reason,
            Integer index
    ) {

        public record Message(
                String role,
                String content
        ) {
        }

    }

    public String getContent() {
        return choices.get(0).message.content;
    }

}
