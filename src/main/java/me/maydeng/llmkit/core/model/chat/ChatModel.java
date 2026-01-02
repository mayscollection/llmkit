package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;
import me.maydeng.llmkit.core.prompt.Prompt;
import me.maydeng.llmkit.core.prompt.SimplePrompt;

import java.util.function.Consumer;

public interface ChatModel {

    default String chat(String prompt) {
        return chat(prompt, new ChatOptions());
    }

    default String chat(String prompt, ChatOptions options) {
        AiMessageResponse response = chat(new SimplePrompt(prompt), options);
        if (response != null && response.isError()) {
            throw new RuntimeException(response.getErrorMessage());
        }
        return response != null && response.getMessage() != null ? response.getMessage().getContent() : null;
    }

    default AiMessageResponse chat(Prompt prompt) {
        return chat(prompt, new ChatOptions());
    }

    AiMessageResponse chat(Prompt prompt, ChatOptions options);

    default void chatStream(String prompt, Consumer<String> onDelta) {
        chatStream(new SimplePrompt(prompt), onDelta, new ChatOptions());
    }

    default void chatStream(String prompt, Consumer<String> onDelta, ChatOptions options) {
        chatStream(new SimplePrompt(prompt), onDelta, options);
    }

    default void chatStream(Prompt prompt, Consumer<String> onDelta, ChatOptions options) {
        if (onDelta == null) {
            throw new IllegalArgumentException("onDelta must not be null");
        }
        chatStream(prompt, (context, response) -> {
            if (response == null || response.getMessage() == null) {
                return;
            }
            String delta = response.getMessage().getContent();
            if (delta != null) {
                onDelta.accept(delta);
            }
        }, options);
    }

    default void chatStream(String prompt, StreamResponseListener listener) {
        chatStream(new SimplePrompt(prompt), listener, new ChatOptions());
    }

    default void chatStream(String prompt, StreamResponseListener listener, ChatOptions options) {
        chatStream(new SimplePrompt(prompt), listener, options);
    }

    default void chatStream(Prompt prompt, StreamResponseListener listener) {
        chatStream(prompt, listener, new ChatOptions());
    }

    void chatStream(Prompt prompt, StreamResponseListener listener, ChatOptions options);
}
