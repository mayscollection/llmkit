package me.maydeng.llmkit.chat.openai;

import me.maydeng.llmkit.core.model.chat.ChatConfig;
import me.maydeng.llmkit.core.model.chat.ChatInterceptor;
import me.maydeng.llmkit.core.util.StringUtil;

import java.util.List;
import java.util.Map;

public class OpenAIChatConfig extends ChatConfig {
    private static final String DEFAULT_PROVIDER = "openai";
    private static final String DEFAULT_MODEL = "gpt-4o";
    private static final String DEFAULT_ENDPOINT = "https://api.openai.com";
    private static final String DEFAULT_REQUEST_PATH = "/v1/chat/completions";

    public OpenAIChatConfig() {
        setProvider(DEFAULT_PROVIDER);
        setEndpoint(DEFAULT_ENDPOINT);
        setRequestPath(DEFAULT_REQUEST_PATH);
        setModel(DEFAULT_MODEL);
    }

    public static Builder builder() {
        return new Builder();
    }

    public OpenAIChatModel toChatModel() {
        return new OpenAIChatModel(this);
    }

    public OpenAIChatModel toChatModel(List<ChatInterceptor> interceptors) {
        return new OpenAIChatModel(this, interceptors);
    }

    public static class Builder {
        private final OpenAIChatConfig config = new OpenAIChatConfig();

        public Builder apiKey(String apiKey) {
            config.setApiKey(apiKey);
            return this;
        }

        public Builder provider(String provider) {
            config.setProvider(provider);
            return this;
        }

        public Builder endpoint(String endpoint) {
            config.setEndpoint(endpoint);
            return this;
        }

        public Builder requestPath(String requestPath) {
            config.setRequestPath(requestPath);
            return this;
        }

        public Builder model(String model) {
            config.setModel(model);
            return this;
        }

        public Builder customProperty(String key, Object value) {
            config.putCustomProperty(key, value);
            return this;
        }

        public Builder customProperties(Map<String, Object> customProperties) {
            config.setCustomProperties(customProperties);
            return this;
        }

        public Builder logEnabled(boolean logEnabled) {
            config.setLogEnabled(logEnabled);
            return this;
        }

        public OpenAIChatConfig build() {
            if (StringUtil.noText(config.getApiKey())) {
                throw new IllegalStateException("apiKey must be set for OpenAIChatConfig");
            }
            return config;
        }

        public OpenAIChatModel buildModel() {
            return new OpenAIChatModel(build());
        }
    }
}
