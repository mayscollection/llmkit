package me.maydeng.llmkit.chat.openai;

import me.maydeng.llmkit.core.model.chat.ChatInterceptor;
import me.maydeng.llmkit.core.model.chat.OpenAICompatibleChatModel;

import java.util.List;

public class OpenAIChatModel extends OpenAICompatibleChatModel<OpenAIChatConfig> {

    public OpenAIChatModel(OpenAIChatConfig config) {
        super(config);
    }

    public OpenAIChatModel(OpenAIChatConfig config, List<ChatInterceptor> userInterceptors) {
        super(config, userInterceptors);
    }
}
