package io.github.llmkit.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ExceptionTest {

    @Test
    void llmKitExceptionShouldWork() {
        LLMKitException ex = new LLMKitException("Test message");
        assertThat(ex.getMessage()).isEqualTo("Test message");
        assertThat(ex.getErrorCode()).isNull();

        LLMKitException exWithCode = new LLMKitException("ERR001", "Test with code", null);
        assertThat(exWithCode.getErrorCode()).isEqualTo("ERR001");
    }

    @Test
    void configurationExceptionShouldWork() {
        ConfigurationException ex = ConfigurationException.missingField("apiKey");
        assertThat(ex.getMessage()).contains("apiKey");

        ConfigurationException invalidEx = ConfigurationException.invalidValue("temperature", 3.0, "must be between 0 and 2");
        assertThat(invalidEx.getMessage()).contains("temperature");
        assertThat(invalidEx.getMessage()).contains("3.0");
        assertThat(invalidEx.getMessage()).contains("must be between 0 and 2");
    }

    @Test
    void chatExceptionShouldWork() {
        ChatException ex = new ChatException("Chat failed", "raw response body", null);
        assertThat(ex.getMessage()).isEqualTo("Chat failed");
        assertThat(ex.getRawResponse()).isEqualTo("raw response body");
    }

    @Test
    void providerExceptionShouldWork() {
        ProviderException ex = new ProviderException(
                "Rate limit exceeded",
                "rate_limit_exceeded",
                "rate_limit_error",
                429,
                "{\"error\": {...}}"
        );

        assertThat(ex.getMessage()).isEqualTo("Rate limit exceeded");
        assertThat(ex.getProviderErrorCode()).isEqualTo("rate_limit_exceeded");
        assertThat(ex.getProviderErrorType()).isEqualTo("rate_limit_error");
        assertThat(ex.getHttpStatusCode()).isEqualTo(429);
        assertThat(ex.isRateLimitError()).isTrue();
        assertThat(ex.isAuthenticationError()).isFalse();
    }

    @Test
    void providerExceptionShouldDetectAuthError() {
        ProviderException ex = new ProviderException(
                "Invalid API key",
                "invalid_api_key",
                "authentication_error",
                401,
                "{\"error\": {...}}"
        );

        assertThat(ex.isAuthenticationError()).isTrue();
        assertThat(ex.isRateLimitError()).isFalse();
    }

    @Test
    void networkExceptionShouldWork() {
        NetworkException ex = new NetworkException("Connection failed", 500, "Server error");
        assertThat(ex.getMessage()).isEqualTo("Connection failed");
        assertThat(ex.getHttpStatusCode()).isEqualTo(500);
        assertThat(ex.getResponseBody()).isEqualTo("Server error");
    }

    @Test
    void parseExceptionShouldWork() {
        ParseException ex = ParseException.invalidJson("{invalid}", new RuntimeException("Parse error"));
        assertThat(ex.getMessage()).contains("JSON");
        assertThat(ex.getRawContent()).isEqualTo("{invalid}");

        ParseException missingField = ParseException.missingField("choices", "{}");
        assertThat(missingField.getMessage()).contains("choices");
    }

    @Test
    void exceptionHierarchyShouldBeCorrect() {
        assertThat(new ConfigurationException("test")).isInstanceOf(LLMKitException.class);
        assertThat(new ChatException("test")).isInstanceOf(LLMKitException.class);
        assertThat(new ProviderException("test", null, null, "{}")).isInstanceOf(ChatException.class);
        assertThat(new NetworkException("test")).isInstanceOf(LLMKitException.class);
        assertThat(new ParseException("test")).isInstanceOf(LLMKitException.class);
    }
}
