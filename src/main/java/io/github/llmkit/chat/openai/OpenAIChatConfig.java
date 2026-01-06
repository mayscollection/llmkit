package io.github.llmkit.chat.openai;

import io.github.llmkit.api.ChatModel;
import io.github.llmkit.core.config.AbstractChatConfigBuilder;
import io.github.llmkit.core.config.ChatConfig;

/**
 * Configuration for OpenAI chat models.
 *
 * <p>Use the builder to create configurations:</p>
 * <pre>{@code
 * OpenAIChatConfig config = OpenAIChatConfig.builder()
 *     .apiKey("your-api-key")
 *     .model("gpt-4")
 *     .build();
 *
 * // Or directly build a model
 * ChatModel model = OpenAIChatConfig.builder()
 *     .apiKey("your-api-key")
 *     .buildModel();
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class OpenAIChatConfig extends ChatConfig {

    private static final String DEFAULT_PROVIDER = "openai";
    private static final String DEFAULT_MODEL = "gpt-4o";
    private static final String DEFAULT_ENDPOINT = "https://api.openai.com";
    private static final String DEFAULT_REQUEST_PATH = "/v1/chat/completions";

    /**
     * Creates a new OpenAIChatConfig with default values.
     */
    public OpenAIChatConfig() {
        setProvider(DEFAULT_PROVIDER);
        setEndpoint(DEFAULT_ENDPOINT);
        setRequestPath(DEFAULT_REQUEST_PATH);
        setModel(DEFAULT_MODEL);
    }

    /**
     * Creates a new builder for OpenAIChatConfig.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Converts this config to a ChatModel.
     *
     * @return a new OpenAIChatModel
     */
    public OpenAIChatModel toModel() {
        return new OpenAIChatModel(this);
    }

    /**
     * Builder for OpenAIChatConfig.
     */
    public static class Builder extends AbstractChatConfigBuilder<OpenAIChatConfig, Builder> {

        public Builder() {
            super(new OpenAIChatConfig());
        }

        @Override
        public ChatModel buildModel() {
            return new OpenAIChatModel(build());
        }
    }
}
