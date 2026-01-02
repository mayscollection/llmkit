package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.util.StringUtil;

import java.util.Map;

public class ChatOptions {
    protected Boolean retryEnabled;
    protected Integer retryCount;
    protected Integer retryInitialDelayMs;
    private String model;
    private Float temperature = 0.5f;
    private Integer maxTokens;
    private Boolean includeUsage;
    private Map<String, Object> extra;
    private boolean streaming;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModelOrDefault(String defaultModel) {
        return StringUtil.hasText(model) ? model : defaultModel;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Integer getMaxTokens() {
        return maxTokens;
    }

    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }

    public Boolean getIncludeUsage() {
        return includeUsage;
    }

    public void setIncludeUsage(Boolean includeUsage) {
        this.includeUsage = includeUsage;
    }

    public Boolean getIncludeUsageOrDefault(Boolean defaultValue) {
        return includeUsage != null ? includeUsage : defaultValue;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    public boolean isStreaming() {
        return streaming;
    }

    public void setStreaming(boolean streaming) {
        this.streaming = streaming;
    }

    public Boolean getRetryEnabledOrDefault(boolean defaultValue) {
        return retryEnabled != null ? retryEnabled : defaultValue;
    }

    public void setRetryEnabled(Boolean retryEnabled) {
        this.retryEnabled = retryEnabled;
    }

    public Integer getRetryCountOrDefault(int defaultValue) {
        return retryCount != null ? retryCount : defaultValue;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    public Integer getRetryInitialDelayMsOrDefault(int defaultValue) {
        return retryInitialDelayMs != null ? retryInitialDelayMs : defaultValue;
    }

    public void setRetryInitialDelayMs(Integer retryInitialDelayMs) {
        this.retryInitialDelayMs = retryInitialDelayMs;
    }
}
