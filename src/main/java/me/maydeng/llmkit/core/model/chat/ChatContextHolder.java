package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.client.ChatRequestSpec;
import me.maydeng.llmkit.core.prompt.Prompt;

public final class ChatContextHolder {
    private static final ThreadLocal<ChatContext> CONTEXT_HOLDER = new ThreadLocal<>();

    private ChatContextHolder() {
    }

    public static ChatContextScope beginChat(Prompt prompt, ChatOptions options, ChatRequestSpec request, ChatConfig config) {
        ChatContext context = new ChatContext();
        context.setPrompt(prompt);
        context.setOptions(options);
        context.setRequestSpec(request);
        context.setConfig(config);
        CONTEXT_HOLDER.set(context);
        return new ChatContextScope(context);
    }

    public static ChatContext currentContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void clear() {
        CONTEXT_HOLDER.remove();
    }

    public static class ChatContextScope implements AutoCloseable {
        private final ChatContext context;

        public ChatContextScope(ChatContext context) {
            this.context = context;
        }

        public ChatContext getContext() {
            return context;
        }

        @Override
        public void close() {
            clear();
        }
    }
}
