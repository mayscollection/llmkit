package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.model.chat.BaseChatModel;
import me.maydeng.llmkit.core.model.chat.StreamResponseListener;
import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;

public abstract class ChatClient {
    protected final BaseChatModel<?> chatModel;

    protected ChatClient(BaseChatModel<?> chatModel) {
        this.chatModel = chatModel;
    }

    public abstract AiMessageResponse chat();

    public abstract void chatStream(StreamResponseListener listener);
}
