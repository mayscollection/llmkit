package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.client.ChatClient;
import me.maydeng.llmkit.core.model.client.ChatRequestSpecBuilder;
import me.maydeng.llmkit.core.model.client.OpenAIChatClient;
import me.maydeng.llmkit.core.model.client.OpenAIChatRequestSpecBuilder;

import java.util.List;

public class OpenAICompatibleChatModel<T extends ChatConfig> extends BaseChatModel<T> {

    public OpenAICompatibleChatModel(T config) {
        super(config);
    }

    public OpenAICompatibleChatModel(T config, List<ChatInterceptor> userInterceptors) {
        super(config, userInterceptors);
    }

    @Override
    public ChatClient getChatClient() {
        if (this.chatClient == null) {
            this.chatClient = new OpenAIChatClient(this);
        }
        return this.chatClient;
    }

    @Override
    public ChatRequestSpecBuilder getChatRequestSpecBuilder() {
        if (this.chatRequestSpecBuilder == null) {
            this.chatRequestSpecBuilder = new OpenAIChatRequestSpecBuilder();
        }
        return this.chatRequestSpecBuilder;
    }
}
