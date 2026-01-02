package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;
import me.maydeng.llmkit.core.model.client.ChatClient;
import me.maydeng.llmkit.core.model.client.ChatRequestSpec;
import me.maydeng.llmkit.core.model.client.ChatRequestSpecBuilder;
import me.maydeng.llmkit.core.prompt.Prompt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseChatModel<T extends ChatConfig> implements ChatModel {
    protected final T config;
    private final List<ChatInterceptor> interceptors;
    protected ChatClient chatClient;
    protected ChatRequestSpecBuilder chatRequestSpecBuilder;

    public BaseChatModel(T config) {
        this(config, Collections.emptyList());
    }

    public BaseChatModel(T config, List<ChatInterceptor> userInterceptors) {
        this.config = config;
        this.interceptors = new ArrayList<>();
        if (userInterceptors != null) {
            this.interceptors.addAll(userInterceptors);
        }
    }

    @Override
    public AiMessageResponse chat(Prompt prompt, ChatOptions options) {
        if (options == null) {
            options = new ChatOptions();
        }
        options.setStreaming(false);

        ChatRequestSpec request = getChatRequestSpecBuilder().buildRequest(prompt, options, config);

        try (ChatContextHolder.ChatContextScope scope =
                     ChatContextHolder.beginChat(prompt, options, request, config)) {
            SyncChain chain = buildSyncChain(0);
            return chain.proceed(this, scope.getContext());
        }
    }

    @Override
    public void chatStream(Prompt prompt, StreamResponseListener listener, ChatOptions options) {
        if (options == null) {
            options = new ChatOptions();
        }
        options.setStreaming(true);

        ChatRequestSpec request = getChatRequestSpecBuilder().buildRequest(prompt, options, config);

        try (ChatContextHolder.ChatContextScope scope =
                     ChatContextHolder.beginChat(prompt, options, request, config)) {
            StreamChain chain = buildStreamChain(0);
            chain.proceed(this, scope.getContext(), listener);
        }
    }

    private SyncChain buildSyncChain(int index) {
        if (index >= interceptors.size()) {
            return (model, context) -> getChatClient().chat();
        }

        ChatInterceptor current = interceptors.get(index);
        SyncChain next = buildSyncChain(index + 1);
        return (model, context) -> current.intercept(model, context, next);
    }

    private StreamChain buildStreamChain(int index) {
        if (index >= interceptors.size()) {
            return (model, context, listener) -> getChatClient().chatStream(listener);
        }

        ChatInterceptor current = interceptors.get(index);
        StreamChain next = buildStreamChain(index + 1);
        return (model, context, listener) -> current.interceptStream(model, context, listener, next);
    }

    public T getConfig() {
        return config;
    }

    public List<ChatInterceptor> getInterceptors() {
        return interceptors;
    }

    public void addInterceptor(ChatInterceptor interceptor) {
        if (interceptor != null) {
            interceptors.add(interceptor);
        }
    }

    public abstract ChatClient getChatClient();

    public abstract ChatRequestSpecBuilder getChatRequestSpecBuilder();
}
