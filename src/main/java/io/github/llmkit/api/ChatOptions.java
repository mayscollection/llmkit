package io.github.llmkit.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Immutable options for chat requests.
 *
 * <p>This class provides configuration options for individual chat requests,
 * such as model selection, temperature, max tokens, and retry settings.</p>
 *
 * <p>Instances are created using the {@link #builder()} method:</p>
 * <pre>{@code
 * ChatOptions options = ChatOptions.builder()
 *     .model("gpt-4")
 *     .temperature(0.7f)
 *     .maxTokens(1000)
 *     .build();
 * }</pre>
 *
 * <p>To create a modified copy, use the {@code with*} methods or {@link #toBuilder()}:</p>
 * <pre>{@code
 * ChatOptions modified = options.withTemperature(0.9f);
 * // or
 * ChatOptions modified = options.toBuilder().temperature(0.9f).build();
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class ChatOptions {

    /**
     * Default chat options with sensible defaults.
     */
    public static final ChatOptions DEFAULT = builder().build();

    private final String model;
    private final Float temperature;
    private final Integer maxTokens;
    private final Double topP;
    private final Double frequencyPenalty;
    private final Double presencePenalty;
    private final Boolean includeUsage;
    private final Map<String, Object> extra;
    private final Boolean retryEnabled;
    private final Integer retryCount;
    private final Integer retryDelayMs;

    private ChatOptions(Builder builder) {
        this.model = builder.model;
        this.temperature = builder.temperature;
        this.maxTokens = builder.maxTokens;
        this.topP = builder.topP;
        this.frequencyPenalty = builder.frequencyPenalty;
        this.presencePenalty = builder.presencePenalty;
        this.includeUsage = builder.includeUsage;
        this.extra = builder.extra != null
                ? Collections.unmodifiableMap(new HashMap<>(builder.extra))
                : Collections.emptyMap();
        this.retryEnabled = builder.retryEnabled;
        this.retryCount = builder.retryCount;
        this.retryDelayMs = builder.retryDelayMs;
    }

    /**
     * Creates a new builder for ChatOptions.
     *
     * @return a new builder instance
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Creates a builder pre-populated with this instance's values.
     *
     * @return a new builder with copied values
     */
    public Builder toBuilder() {
        return new Builder()
                .model(this.model)
                .temperature(this.temperature)
                .maxTokens(this.maxTokens)
                .topP(this.topP)
                .frequencyPenalty(this.frequencyPenalty)
                .presencePenalty(this.presencePenalty)
                .includeUsage(this.includeUsage)
                .extra(this.extra.isEmpty() ? null : new HashMap<>(this.extra))
                .retryEnabled(this.retryEnabled)
                .retryCount(this.retryCount)
                .retryDelayMs(this.retryDelayMs);
    }

    // ========== With methods for creating modified copies ==========

    /**
     * Returns a new ChatOptions with the specified model.
     *
     * @param model the model name
     * @return a new ChatOptions instance
     */
    public ChatOptions withModel(String model) {
        return toBuilder().model(model).build();
    }

    /**
     * Returns a new ChatOptions with the specified temperature.
     *
     * @param temperature the temperature value
     * @return a new ChatOptions instance
     */
    public ChatOptions withTemperature(Float temperature) {
        return toBuilder().temperature(temperature).build();
    }

    /**
     * Returns a new ChatOptions with the specified max tokens.
     *
     * @param maxTokens the maximum number of tokens
     * @return a new ChatOptions instance
     */
    public ChatOptions withMaxTokens(Integer maxTokens) {
        return toBuilder().maxTokens(maxTokens).build();
    }

    // ========== Getters ==========

    /**
     * Returns the model name.
     *
     * @return the model name, or null if not set
     */
    public String getModel() {
        return model;
    }

    /**
     * Returns the model name or a default value.
     *
     * @param defaultModel the default model to return if not set
     * @return the model name or the default
     */
    public String getModelOrDefault(String defaultModel) {
        return model != null && !model.isEmpty() ? model : defaultModel;
    }

    /**
     * Returns the temperature setting.
     *
     * @return the temperature, or null if not set
     */
    public Float getTemperature() {
        return temperature;
    }

    /**
     * Returns the temperature or a default value.
     *
     * @param defaultValue the default value
     * @return the temperature or the default
     */
    public Float getTemperatureOrDefault(Float defaultValue) {
        return temperature != null ? temperature : defaultValue;
    }

    /**
     * Returns the maximum number of tokens.
     *
     * @return the max tokens, or null if not set
     */
    public Integer getMaxTokens() {
        return maxTokens;
    }

    /**
     * Returns the top-p (nucleus sampling) setting.
     *
     * @return the top-p value, or null if not set
     */
    public Double getTopP() {
        return topP;
    }

    /**
     * Returns the frequency penalty.
     *
     * @return the frequency penalty, or null if not set
     */
    public Double getFrequencyPenalty() {
        return frequencyPenalty;
    }

    /**
     * Returns the presence penalty.
     *
     * @return the presence penalty, or null if not set
     */
    public Double getPresencePenalty() {
        return presencePenalty;
    }

    /**
     * Returns whether to include usage information in the response.
     *
     * @return true to include usage info, or null if not set
     */
    public Boolean getIncludeUsage() {
        return includeUsage;
    }

    /**
     * Returns the include usage setting or a default value.
     *
     * @param defaultValue the default value
     * @return the include usage setting or the default
     */
    public Boolean getIncludeUsageOrDefault(Boolean defaultValue) {
        return includeUsage != null ? includeUsage : defaultValue;
    }

    /**
     * Returns the extra parameters map.
     *
     * @return an unmodifiable map of extra parameters
     */
    public Map<String, Object> getExtra() {
        return extra;
    }

    /**
     * Returns whether retry is enabled.
     *
     * @return true if retry is enabled, or null if not set
     */
    public Boolean getRetryEnabled() {
        return retryEnabled;
    }

    /**
     * Returns the retry enabled setting or a default value.
     *
     * @param defaultValue the default value
     * @return the retry enabled setting or the default
     */
    public Boolean getRetryEnabledOrDefault(Boolean defaultValue) {
        return retryEnabled != null ? retryEnabled : defaultValue;
    }

    /**
     * Returns the retry count.
     *
     * @return the retry count, or null if not set
     */
    public Integer getRetryCount() {
        return retryCount;
    }

    /**
     * Returns the retry count or a default value.
     *
     * @param defaultValue the default value
     * @return the retry count or the default
     */
    public Integer getRetryCountOrDefault(Integer defaultValue) {
        return retryCount != null ? retryCount : defaultValue;
    }

    /**
     * Returns the retry delay in milliseconds.
     *
     * @return the retry delay, or null if not set
     */
    public Integer getRetryDelayMs() {
        return retryDelayMs;
    }

    /**
     * Returns the retry delay or a default value.
     *
     * @param defaultValue the default value
     * @return the retry delay or the default
     */
    public Integer getRetryDelayMsOrDefault(Integer defaultValue) {
        return retryDelayMs != null ? retryDelayMs : defaultValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatOptions)) return false;
        ChatOptions that = (ChatOptions) o;
        return Objects.equals(model, that.model) &&
                Objects.equals(temperature, that.temperature) &&
                Objects.equals(maxTokens, that.maxTokens) &&
                Objects.equals(topP, that.topP) &&
                Objects.equals(frequencyPenalty, that.frequencyPenalty) &&
                Objects.equals(presencePenalty, that.presencePenalty) &&
                Objects.equals(includeUsage, that.includeUsage) &&
                Objects.equals(extra, that.extra) &&
                Objects.equals(retryEnabled, that.retryEnabled) &&
                Objects.equals(retryCount, that.retryCount) &&
                Objects.equals(retryDelayMs, that.retryDelayMs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, temperature, maxTokens, topP, frequencyPenalty,
                presencePenalty, includeUsage, extra, retryEnabled, retryCount, retryDelayMs);
    }

    @Override
    public String toString() {
        return "ChatOptions{" +
                "model='" + model + '\'' +
                ", temperature=" + temperature +
                ", maxTokens=" + maxTokens +
                ", topP=" + topP +
                ", frequencyPenalty=" + frequencyPenalty +
                ", presencePenalty=" + presencePenalty +
                ", includeUsage=" + includeUsage +
                ", extra=" + extra +
                ", retryEnabled=" + retryEnabled +
                ", retryCount=" + retryCount +
                ", retryDelayMs=" + retryDelayMs +
                '}';
    }

    /**
     * Builder for creating {@link ChatOptions} instances.
     */
    public static final class Builder {
        private String model;
        private Float temperature = 0.5f;
        private Integer maxTokens;
        private Double topP;
        private Double frequencyPenalty;
        private Double presencePenalty;
        private Boolean includeUsage;
        private Map<String, Object> extra;
        private Boolean retryEnabled;
        private Integer retryCount;
        private Integer retryDelayMs;

        private Builder() {
        }

        /**
         * Sets the model name.
         *
         * @param model the model name (e.g., "gpt-4", "gpt-3.5-turbo")
         * @return this builder
         */
        public Builder model(String model) {
            this.model = model;
            return this;
        }

        /**
         * Sets the temperature for response randomness.
         *
         * @param temperature the temperature (0.0 to 2.0, lower is more deterministic)
         * @return this builder
         */
        public Builder temperature(Float temperature) {
            this.temperature = temperature;
            return this;
        }

        /**
         * Sets the maximum number of tokens in the response.
         *
         * @param maxTokens the maximum tokens
         * @return this builder
         */
        public Builder maxTokens(Integer maxTokens) {
            this.maxTokens = maxTokens;
            return this;
        }

        /**
         * Sets the top-p (nucleus sampling) value.
         *
         * @param topP the top-p value (0.0 to 1.0)
         * @return this builder
         */
        public Builder topP(Double topP) {
            this.topP = topP;
            return this;
        }

        /**
         * Sets the frequency penalty.
         *
         * @param frequencyPenalty the frequency penalty (-2.0 to 2.0)
         * @return this builder
         */
        public Builder frequencyPenalty(Double frequencyPenalty) {
            this.frequencyPenalty = frequencyPenalty;
            return this;
        }

        /**
         * Sets the presence penalty.
         *
         * @param presencePenalty the presence penalty (-2.0 to 2.0)
         * @return this builder
         */
        public Builder presencePenalty(Double presencePenalty) {
            this.presencePenalty = presencePenalty;
            return this;
        }

        /**
         * Sets whether to include usage information.
         *
         * @param includeUsage true to include usage stats
         * @return this builder
         */
        public Builder includeUsage(Boolean includeUsage) {
            this.includeUsage = includeUsage;
            return this;
        }

        /**
         * Sets extra parameters to pass to the API.
         *
         * @param extra a map of extra parameters
         * @return this builder
         */
        public Builder extra(Map<String, Object> extra) {
            this.extra = extra;
            return this;
        }

        /**
         * Adds an extra parameter.
         *
         * @param key   the parameter key
         * @param value the parameter value
         * @return this builder
         */
        public Builder addExtra(String key, Object value) {
            if (this.extra == null) {
                this.extra = new HashMap<>();
            }
            this.extra.put(key, value);
            return this;
        }

        /**
         * Sets whether retry is enabled.
         *
         * @param retryEnabled true to enable retry
         * @return this builder
         */
        public Builder retryEnabled(Boolean retryEnabled) {
            this.retryEnabled = retryEnabled;
            return this;
        }

        /**
         * Sets the number of retry attempts.
         *
         * @param retryCount the retry count
         * @return this builder
         */
        public Builder retryCount(Integer retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        /**
         * Sets the initial retry delay in milliseconds.
         *
         * @param retryDelayMs the retry delay
         * @return this builder
         */
        public Builder retryDelayMs(Integer retryDelayMs) {
            this.retryDelayMs = retryDelayMs;
            return this;
        }

        /**
         * Builds the ChatOptions instance.
         *
         * @return a new ChatOptions instance
         */
        public ChatOptions build() {
            return new ChatOptions(this);
        }
    }
}
