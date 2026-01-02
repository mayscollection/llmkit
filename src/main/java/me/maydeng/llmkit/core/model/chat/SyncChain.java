package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;

@FunctionalInterface
public interface SyncChain {
    AiMessageResponse proceed(BaseChatModel<?> model, ChatContext context);
}
