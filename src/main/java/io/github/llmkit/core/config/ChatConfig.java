package io.github.llmkit.core.config;

/**
 * Configuration for chat models.
 *
 * <p>This class extends {@link BaseModelConfig} with chat-specific settings
 * like logging and retry configuration.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
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
