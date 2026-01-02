package me.maydeng.llmkit.chat.qwen;

import me.maydeng.llmkit.core.model.chat.ChatInterceptor;
import me.maydeng.llmkit.core.model.chat.OpenAICompatibleChatModel;

import java.util.List;

public class QwenChatModel extends OpenAICompatibleChatModel<QwenChatConfig> {

    public QwenChatModel(QwenChatConfig config) {
        super(config);
    }

    public QwenChatModel(QwenChatConfig config, List<ChatInterceptor> userInterceptors) {
        super(config, userInterceptors);
    }
}
