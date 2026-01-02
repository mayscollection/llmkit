package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.model.chat.ChatConfig;
import me.maydeng.llmkit.core.model.chat.ChatOptions;
import me.maydeng.llmkit.core.prompt.Prompt;

public interface ChatRequestSpecBuilder {
    ChatRequestSpec buildRequest(Prompt prompt, ChatOptions options, ChatConfig config);
}
