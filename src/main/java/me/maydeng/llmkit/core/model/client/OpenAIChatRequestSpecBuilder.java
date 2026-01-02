package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.message.Message;
import me.maydeng.llmkit.core.model.chat.ChatConfig;
import me.maydeng.llmkit.core.model.chat.ChatOptions;
import me.maydeng.llmkit.core.prompt.Prompt;
import me.maydeng.llmkit.core.util.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAIChatRequestSpecBuilder implements ChatRequestSpecBuilder {

    protected ChatMessageSerializer chatMessageSerializer;

    public OpenAIChatRequestSpecBuilder() {
        this(new OpenAIChatMessageSerializer());
    }

    public OpenAIChatRequestSpecBuilder(ChatMessageSerializer chatMessageSerializer) {
        this.chatMessageSerializer = chatMessageSerializer;
    }

    @Override
    public ChatRequestSpec buildRequest(Prompt prompt, ChatOptions options, ChatConfig config) {
        String url = buildRequestUrl(config);
        Map<String, String> headers = buildRequestHeaders(config);
        String body = buildRequestBody(prompt, options, config);

        boolean retryEnabled = options.getRetryEnabledOrDefault(config.isRetryEnabled());
        int retryCount = options.getRetryCountOrDefault(config.getRetryCount());
        int retryDelayMs = options.getRetryInitialDelayMsOrDefault(config.getRetryInitialDelayMs());

        return new ChatRequestSpec(url, headers, body, retryEnabled ? retryCount : 0, retryEnabled ? retryDelayMs : 0);
    }

    protected String buildRequestUrl(ChatConfig config) {
        return config.getFullUrl();
    }

    protected Map<String, String> buildRequestHeaders(ChatConfig config) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + config.getApiKey());
        return headers;
    }

    protected String buildRequestBody(Prompt prompt, ChatOptions options, ChatConfig config) {
        List<Message> messages = prompt.getMessages();
        List<Map<String, Object>> serialized = chatMessageSerializer.serializeMessages(messages, config);

        Maps result = Maps.of("model", options.getModelOrDefault(config.getModel()))
                .setIf(options.isStreaming(), "stream", true)
                .setIfNotNull("temperature", options.getTemperature())
                .setIfNotNull("max_tokens", options.getMaxTokens())
                .set("messages", serialized);

        if (options.isStreaming() && options.getIncludeUsageOrDefault(true)) {
            result.set("stream_options", Maps.of("include_usage", true));
        }

        if (options.getExtra() != null && !options.getExtra().isEmpty()) {
            result.putAll(options.getExtra());
        }

        return result.toJson();
    }

    public ChatMessageSerializer getChatMessageSerializer() {
        return chatMessageSerializer;
    }

    public void setChatMessageSerializer(ChatMessageSerializer chatMessageSerializer) {
        this.chatMessageSerializer = chatMessageSerializer;
    }
}
