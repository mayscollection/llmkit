package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.model.chat.ChatContext;
import me.maydeng.llmkit.core.model.chat.ChatModel;

public class StreamContext {
    private final ChatModel chatModel;
    private final ChatContext chatContext;
    private final StreamClient client;
    private AiMessage aiMessage;
    private Throwable throwable;

    public StreamContext(ChatModel chatModel, ChatContext chatContext, StreamClient client) {
        this.chatModel = chatModel;
        this.chatContext = chatContext;
        this.client = client;
    }

    public ChatModel getChatModel() {
        return chatModel;
    }

    public ChatContext getChatContext() {
        return chatContext;
    }

    public StreamClient getClient() {
        return client;
    }

    public AiMessage getAiMessage() {
        return aiMessage;
    }

    public void setAiMessage(AiMessage aiMessage) {
        this.aiMessage = aiMessage;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isError() {
        return throwable != null;
    }
}
