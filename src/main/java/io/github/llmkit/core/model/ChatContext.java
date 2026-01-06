package io.github.llmkit.core.model;

import io.github.llmkit.api.ChatOptions;
import io.github.llmkit.prompt.Prompt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Immutable context for a chat request.
 *
 * <p>This class encapsulates all the information needed to process a chat request,
 * including the prompt, options, and request metadata. It is immutable and thread-safe.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class ChatContext {

    private final Prompt prompt;
    private final ChatOptions options;
    private final String requestUrl;
    private final Map<String, String> requestHeaders;
    private final String requestBody;
    private final boolean streaming;
    private final int retryCount;
    private final int retryDelayMs;

    private ChatContext(Builder builder) {
        this.prompt = builder.prompt;
        this.options = builder.options != null ? builder.options : ChatOptions.DEFAULT;
        this.requestUrl = builder.requestUrl;
        this.requestHeaders = builder.requestHeaders != null
                ? Collections.unmodifiableMap(new HashMap<>(builder.requestHeaders))
                : Collections.emptyMap();
        this.requestBody = builder.requestBody;
        this.streaming = builder.streaming;
        this.retryCount = builder.retryCount;
        this.retryDelayMs = builder.retryDelayMs;
    }

    /**
     * Creates a new builder for ChatContext.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Returns the prompt for this request.
     *
     * @return the prompt
     */
    public Prompt getPrompt() {
        return prompt;
    }

    /**
     * Returns the chat options for this request.
     *
     * @return the options
     */
    public ChatOptions getOptions() {
        return options;
    }

    /**
     * Returns the request URL.
     *
     * @return the URL
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * Returns the request headers.
     *
     * @return an unmodifiable map of headers
     */
    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * Returns the request body.
     *
     * @return the request body JSON string
     */
    public String getRequestBody() {
        return requestBody;
    }

    /**
     * Returns whether this is a streaming request.
     *
     * @return true if streaming
     */
    public boolean isStreaming() {
        return streaming;
    }

    /**
     * Returns the retry count for this request.
     *
     * @return the retry count
     */
    public int getRetryCount() {
        return retryCount;
    }

    /**
     * Returns the retry delay in milliseconds.
     *
     * @return the retry delay
     */
    public int getRetryDelayMs() {
        return retryDelayMs;
    }

    /**
     * Builder for creating {@link ChatContext} instances.
     */
    public static final class Builder {
        private Prompt prompt;
        private ChatOptions options;
        private String requestUrl;
        private Map<String, String> requestHeaders;
        private String requestBody;
        private boolean streaming;
        private int retryCount;
        private int retryDelayMs;

        private Builder() {
        }

        /**
         * Sets the prompt.
         *
         * @param prompt the prompt
         * @return this builder
         */
        public Builder prompt(Prompt prompt) {
            this.prompt = prompt;
            return this;
        }

        /**
         * Sets the chat options.
         *
         * @param options the options
         * @return this builder
         */
        public Builder options(ChatOptions options) {
            this.options = options;
            return this;
        }

        /**
         * Sets the request URL.
         *
         * @param requestUrl the URL
         * @return this builder
         */
        public Builder requestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        /**
         * Sets the request headers.
         *
         * @param requestHeaders the headers map
         * @return this builder
         */
        public Builder requestHeaders(Map<String, String> requestHeaders) {
            this.requestHeaders = requestHeaders;
            return this;
        }

        /**
         * Sets the request body.
         *
         * @param requestBody the body JSON string
         * @return this builder
         */
        public Builder requestBody(String requestBody) {
            this.requestBody = requestBody;
            return this;
        }

        /**
         * Sets whether this is a streaming request.
         *
         * @param streaming true for streaming
         * @return this builder
         */
        public Builder streaming(boolean streaming) {
            this.streaming = streaming;
            return this;
        }

        /**
         * Sets the retry count.
         *
         * @param retryCount the retry count
         * @return this builder
         */
        public Builder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        /**
         * Sets the retry delay.
         *
         * @param retryDelayMs the delay in milliseconds
         * @return this builder
         */
        public Builder retryDelayMs(int retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
            return this;
        }

        /**
         * Builds the ChatContext instance.
         *
         * @return a new ChatContext instance
         */
        public ChatContext build() {
            return new ChatContext(this);
        }
    }
}
