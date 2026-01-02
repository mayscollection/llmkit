package me.maydeng.llmkit.chat.qwen;

import me.maydeng.llmkit.core.model.chat.ChatConfig;
import me.maydeng.llmkit.core.model.chat.ChatInterceptor;
import me.maydeng.llmkit.core.util.StringUtil;

import java.util.List;
import java.util.Map;

public class QwenChatConfig extends ChatConfig {
    private static final String DEFAULT_PROVIDER = "qwen";
    private static final String DEFAULT_MODEL = "qwen-turbo";
    private static final String DEFAULT_ENDPOINT = "https://dashscope.aliyuncs.com";
    private static final String DEFAULT_REQUEST_PATH = "/compatible-mode/v1/chat/completions";

    public QwenChatConfig() {
        setProvider(DEFAULT_PROVIDER);
        setEndpoint(DEFAULT_ENDPOINT);
        setRequestPath(DEFAULT_REQUEST_PATH);
        setModel(DEFAULT_MODEL);
    }

    public static Builder builder() {
        return new Builder();
    }

    public QwenChatModel toChatModel() {
        return new QwenChatModel(this);
    }

    public QwenChatModel toChatModel(List<ChatInterceptor> interceptors) {
        return new QwenChatModel(this, interceptors);
    }

    public static class Builder {
        private final QwenChatConfig config = new QwenChatConfig();

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

        public QwenChatConfig build() {
            if (StringUtil.noText(config.getApiKey())) {
                throw new IllegalStateException("apiKey must be set for QwenChatConfig");
            }
            return config;
        }

        public QwenChatModel buildModel() {
            return new QwenChatModel(build());
        }
    }
}
