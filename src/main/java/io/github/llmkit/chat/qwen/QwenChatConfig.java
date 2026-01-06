package io.github.llmkit.chat.qwen;

import io.github.llmkit.api.ChatModel;
import io.github.llmkit.core.config.AbstractChatConfigBuilder;
import io.github.llmkit.core.config.ChatConfig;

/**
 * Configuration for Alibaba Qwen chat models.
 *
 * <p>Qwen models use an OpenAI-compatible API format. Use the builder to create configurations:</p>
 * <pre>{@code
 * QwenChatConfig config = QwenChatConfig.builder()
 *     .apiKey("your-api-key")
 *     .model("qwen-turbo")
 *     .build();
 *
 * // Or directly build a model
 * ChatModel model = QwenChatConfig.builder()
 *     .apiKey("your-api-key")
 *     .buildModel();
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class QwenChatConfig extends ChatConfig {

    private static final String DEFAULT_PROVIDER = "qwen";
    private static final String DEFAULT_MODEL = "qwen-turbo";
    private static final String DEFAULT_ENDPOINT = "https://dashscope.aliyuncs.com";
    private static final String DEFAULT_REQUEST_PATH = "/compatible-mode/v1/chat/completions";

    /**
     * Creates a new QwenChatConfig with default values.
     */
    public QwenChatConfig() {
        setProvider(DEFAULT_PROVIDER);
        setEndpoint(DEFAULT_ENDPOINT);
        setRequestPath(DEFAULT_REQUEST_PATH);
        setModel(DEFAULT_MODEL);
    }

    /**
     * Creates a new builder for QwenChatConfig.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this config to a ChatModel.
     *
     * @return a new QwenChatModel
     */
    public QwenChatModel toModel() {
        return new QwenChatModel(this);
    }

    /**
     * Builder for QwenChatConfig.
     */
    public static class Builder extends AbstractChatConfigBuilder<QwenChatConfig, Builder> {

        public Builder() {
            super(new QwenChatConfig());
        }

        @Override
        public ChatModel buildModel() {
            return new QwenChatModel(build());
        }
    }
}
