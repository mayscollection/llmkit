package io.github.llmkit.core.config;

import io.github.llmkit.api.ChatModel;
import io.github.llmkit.exception.ConfigurationException;
import io.github.llmkit.util.StringUtil;

import java.util.Map;

/**
 * Abstract builder for ChatConfig subclasses.
 *
 * <p>This class reduces code duplication in provider-specific config builders
 * by providing common builder methods.</p>
 *
 * @param <C> the config type
 * @param <B> the builder type (for fluent API)
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public abstract class AbstractChatConfigBuilder<C extends ChatConfig, B extends AbstractChatConfigBuilder<C, B>> {

    protected final C config;

    protected AbstractChatConfigBuilder(C config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    /**
     * Sets the API key.
     *
     * @param apiKey the API key
     * @return this builder
     */
    public B apiKey(String apiKey) {
        config.setApiKey(apiKey);
        return self();
    }

    /**
     * Sets the provider name.
     *
     * @param provider the provider name
     * @return this builder
     */
    public B provider(String provider) {
        config.setProvider(provider);
        return self();
    }

    /**
     * Sets the API endpoint.
     *
     * @param endpoint the endpoint URL
     * @return this builder
     */
    public B endpoint(String endpoint) {
        config.setEndpoint(endpoint);
        return self();
    }

    /**
     * Sets the request path.
     *
     * @param requestPath the request path
     * @return this builder
     */
    public B requestPath(String requestPath) {
        config.setRequestPath(requestPath);
        return self();
    }

    /**
     * Sets the model name.
     *
     * @param model the model name
     * @return this builder
     */
    public B model(String model) {
        config.setModel(model);
        return self();
    }

    /**
     * Enables or disables logging.
     *
     * @param logEnabled true to enable logging
     * @return this builder
     */
    public B logEnabled(boolean logEnabled) {
        config.setLogEnabled(logEnabled);
        return self();
    }

    /**
     * Enables or disables retry.
     *
     * @param retryEnabled true to enable retry
     * @return this builder
     */
    public B retryEnabled(boolean retryEnabled) {
        config.setRetryEnabled(retryEnabled);
        return self();
    }

    /**
     * Sets the retry count.
     *
     * @param retryCount the number of retries
     * @return this builder
     */
    public B retryCount(int retryCount) {
        config.setRetryCount(retryCount);
        return self();
    }

    /**
     * Sets the retry delay.
     *
     * @param retryDelayMs the delay in milliseconds
     * @return this builder
     */
    public B retryDelayMs(int retryDelayMs) {
        config.setRetryInitialDelayMs(retryDelayMs);
        return self();
    }

    /**
     * Adds a custom property.
     *
     * @param key   the property key
     * @param value the property value
     * @return this builder
     */
    public B customProperty(String key, Object value) {
        config.putCustomProperty(key, value);
        return self();
    }

    /**
     * Sets custom properties.
     *
     * @param customProperties the properties map
     * @return this builder
     */
    public B customProperties(Map<String, Object> customProperties) {
        config.setCustomProperties(customProperties);
        return self();
    }

    /**
     * Validates the configuration.
     *
     * @throws ConfigurationException if validation fails
     */
    protected void validate() {
        if (StringUtil.noText(config.getApiKey())) {
            throw ConfigurationException.missingField("apiKey");
        }
    }

    /**
     * Builds the configuration.
     *
     * @return the built config
     * @throws ConfigurationException if validation fails
     */
    public C build() {
        validate();
        return config;
    }

    /**
     * Builds and returns a ChatModel.
     *
     * @return the chat model
     */
    public abstract ChatModel buildModel();
}
