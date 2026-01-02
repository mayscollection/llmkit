package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.client.ChatRequestSpec;
import me.maydeng.llmkit.core.prompt.Prompt;

public class ChatContext {
    private Prompt prompt;
    private ChatConfig config;
    private ChatOptions options;
    private ChatRequestSpec requestSpec;

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public ChatConfig getConfig() {
        return config;
    }

    public void setConfig(ChatConfig config) {
        this.config = config;
    }

    public ChatOptions getOptions() {
        return options;
    }

    public void setOptions(ChatOptions options) {
        this.options = options;
    }

    public ChatRequestSpec getRequestSpec() {
        return requestSpec;
    }

    public void setRequestSpec(ChatRequestSpec requestSpec) {
        this.requestSpec = requestSpec;
    }
}
