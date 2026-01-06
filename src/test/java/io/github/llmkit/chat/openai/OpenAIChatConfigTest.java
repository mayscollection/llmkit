package io.github.llmkit.chat.openai;

import io.github.llmkit.exception.ConfigurationException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class OpenAIChatConfigTest {

    @Test
    void shouldHaveDefaultValues() {
        OpenAIChatConfig config = new OpenAIChatConfig();

        assertThat(config.getProvider()).isEqualTo("openai");
        assertThat(config.getEndpoint()).isEqualTo("https://api.openai.com");
        assertThat(config.getRequestPath()).isEqualTo("/v1/chat/completions");
        assertThat(config.getModel()).isEqualTo("gpt-4o");
        assertThat(config.isRetryEnabled()).isTrue();
        assertThat(config.getRetryCount()).isEqualTo(3);
    }

    @Test
    void shouldBuildWithCustomValues() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("test-key")
                .model("gpt-3.5-turbo")
                .endpoint("https://custom.endpoint.com")
                .retryCount(5)
                .build();

        assertThat(config.getApiKey()).isEqualTo("test-key");
        assertThat(config.getModel()).isEqualTo("gpt-3.5-turbo");
        assertThat(config.getEndpoint()).isEqualTo("https://custom.endpoint.com");
        assertThat(config.getRetryCount()).isEqualTo(5);
    }

    @Test
    void shouldNormalizeEndpoint() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("test-key")
                .endpoint("https://api.openai.com/")
                .build();

        assertThat(config.getEndpoint()).isEqualTo("https://api.openai.com");
    }

    @Test
    void shouldNormalizeRequestPath() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("test-key")
                .requestPath("v1/chat/completions")
                .build();

        assertThat(config.getRequestPath()).isEqualTo("/v1/chat/completions");
    }

    @Test
    void shouldRequireApiKey() {
        assertThatThrownBy(() -> OpenAIChatConfig.builder().build())
                .isInstanceOf(ConfigurationException.class)
                .hasMessageContaining("apiKey");
    }

    @Test
    void shouldBuildModel() {
        OpenAIChatModel model = (OpenAIChatModel) OpenAIChatConfig.builder()
                .apiKey("test-key")
                .buildModel();

        assertThat(model).isNotNull();
        assertThat(model.getConfig().getApiKey()).isEqualTo("test-key");
    }

    @Test
    void toModelShouldWork() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("test-key")
                .build();

        OpenAIChatModel model = config.toModel();

        assertThat(model).isNotNull();
        assertThat(model.getConfig()).isSameAs(config);
    }

    @Test
    void shouldSupportCustomProperties() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("test-key")
                .customProperty("org_id", "org-123")
                .build();

        assertThat(config.getCustomProperties())
                .containsEntry("org_id", "org-123");
    }

    @Test
    void getFullUrlShouldWork() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("test-key")
                .build();

        assertThat(config.getFullUrl()).isEqualTo("https://api.openai.com/v1/chat/completions");
    }

    @Test
    void toStringShouldRedactApiKey() {
        OpenAIChatConfig config = OpenAIChatConfig.builder()
                .apiKey("sk-secret-key-12345")
                .build();

        String toString = config.toString();
        assertThat(toString).doesNotContain("sk-secret-key-12345");
        assertThat(toString).contains("[REDACTED]");
    }
}
