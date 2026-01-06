package io.github.llmkit.api;

import io.github.llmkit.message.AiMessage;

import java.util.Objects;

/**
 * Response from a chat request.
 *
 * <p>This class encapsulates the response from an LLM, including the
 * generated message, raw response, and optional usage statistics.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class ChatResponse {

    private final AiMessage message;
    private final String rawResponse;
    private final Usage usage;
    private final String finishReason;

    private ChatResponse(Builder builder) {
        this.message = builder.message;
        this.rawResponse = builder.rawResponse;
        this.usage = builder.usage;
        this.finishReason = builder.finishReason;
    }

    /**
     * Creates a new builder for ChatResponse.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a simple response with just a message.
     *
     * @param content the message content
     * @return a new ChatResponse
     */
    public static ChatResponse of(String content) {
        return builder().message(new AiMessage(content)).build();
    }

    /**
     * Returns the AI message.
     *
     * @return the message
     */
    public AiMessage getMessage() {
        return message;
    }

    /**
     * Returns the message content as a string.
     *
     * @return the content, or null if no message
     */
    public String getContent() {
        return message != null ? message.getContent() : null;
    }

    /**
     * Returns the raw response from the API.
     *
     * @return the raw response JSON
     */
    public String getRawResponse() {
        return rawResponse;
    }

    /**
     * Returns the usage statistics.
     *
     * @return the usage, or null if not available
     */
    public Usage getUsage() {
        return usage;
    }

    /**
     * Returns the finish reason.
     *
     * @return the finish reason (e.g., "stop", "length")
     */
    public String getFinishReason() {
        return finishReason;
    }

    @Override
    public String toString() {
        return "ChatResponse{" +
                "content='" + (message != null ? truncate(message.getContent(), 50) : "null") + '\'' +
                ", finishReason='" + finishReason + '\'' +
                ", usage=" + usage +
                '}';
    }

    private String truncate(String s, int maxLen) {
        if (s == null) return null;
        return s.length() > maxLen ? s.substring(0, maxLen) + "..." : s;
    }

    /**
     * Token usage statistics.
     */
    public static final class Usage {
        private final int promptTokens;
        private final int completionTokens;
        private final int totalTokens;

        public Usage(int promptTokens, int completionTokens, int totalTokens) {
            this.promptTokens = promptTokens;
            this.completionTokens = completionTokens;
            this.totalTokens = totalTokens;
        }

        public int getPromptTokens() {
            return promptTokens;
        }

        public int getCompletionTokens() {
            return completionTokens;
        }

        public int getTotalTokens() {
            return totalTokens;
        }

        @Override
        public String toString() {
            return "Usage{" +
                    "promptTokens=" + promptTokens +
                    ", completionTokens=" + completionTokens +
                    ", totalTokens=" + totalTokens +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Usage)) return false;
            Usage usage = (Usage) o;
            return promptTokens == usage.promptTokens &&
                    completionTokens == usage.completionTokens &&
                    totalTokens == usage.totalTokens;
        }

        @Override
        public int hashCode() {
            return Objects.hash(promptTokens, completionTokens, totalTokens);
        }
    }

    /**
     * Builder for ChatResponse.
     */
    public static final class Builder {
        private AiMessage message;
        private String rawResponse;
        private Usage usage;
        private String finishReason;

        private Builder() {
        }

        public Builder message(AiMessage message) {
            this.message = message;
            return this;
        }

        public Builder content(String content) {
            this.message = content != null ? new AiMessage(content) : null;
            return this;
        }

        public Builder rawResponse(String rawResponse) {
            this.rawResponse = rawResponse;
            return this;
        }

        public Builder usage(Usage usage) {
            this.usage = usage;
            return this;
        }

        public Builder usage(int promptTokens, int completionTokens, int totalTokens) {
            this.usage = new Usage(promptTokens, completionTokens, totalTokens);
            return this;
        }

        public Builder finishReason(String finishReason) {
            this.finishReason = finishReason;
            return this;
        }

        public ChatResponse build() {
            return new ChatResponse(this);
        }
    }
}
