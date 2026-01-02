package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;

public interface ChatInterceptor {
    AiMessageResponse intercept(BaseChatModel<?> model, ChatContext context, SyncChain chain);

    void interceptStream(BaseChatModel<?> model, ChatContext context, StreamResponseListener listener, StreamChain chain);
}
