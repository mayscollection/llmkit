package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.config.BaseModelConfig;

public class ChatConfig extends BaseModelConfig {
    protected boolean logEnabled = true;
    protected boolean retryEnabled = true;
    protected int retryCount = 3;
    protected int retryInitialDelayMs = 1000;

    public boolean isLogEnabled() {
        return logEnabled;
    }

    public void setLogEnabled(boolean logEnabled) {
        this.logEnabled = logEnabled;
    }

    public boolean isRetryEnabled() {
        return retryEnabled;
    }

    public void setRetryEnabled(boolean retryEnabled) {
        this.retryEnabled = retryEnabled;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryInitialDelayMs() {
        return retryInitialDelayMs;
    }

    public void setRetryInitialDelayMs(int retryInitialDelayMs) {
        this.retryInitialDelayMs = retryInitialDelayMs;
    }
}
