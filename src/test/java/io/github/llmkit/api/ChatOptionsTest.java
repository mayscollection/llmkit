package io.github.llmkit.api;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class ChatOptionsTest {

    @Test
    void shouldCreateWithDefaults() {
        ChatOptions options = ChatOptions.builder().build();

        assertThat(options.getModel()).isNull();
        assertThat(options.getTemperature()).isEqualTo(0.5f);
        assertThat(options.getMaxTokens()).isNull();
        assertThat(options.getExtra()).isEmpty();
    }

    @Test
    void shouldCreateWithCustomValues() {
        ChatOptions options = ChatOptions.builder()
                .model("gpt-4")
                .temperature(0.7f)
                .maxTokens(1000)
                .topP(0.9)
                .frequencyPenalty(0.5)
                .presencePenalty(0.5)
                .includeUsage(true)
                .retryEnabled(true)
                .retryCount(5)
                .retryDelayMs(2000)
                .build();

        assertThat(options.getModel()).isEqualTo("gpt-4");
        assertThat(options.getTemperature()).isEqualTo(0.7f);
        assertThat(options.getMaxTokens()).isEqualTo(1000);
        assertThat(options.getTopP()).isEqualTo(0.9);
        assertThat(options.getFrequencyPenalty()).isEqualTo(0.5);
        assertThat(options.getPresencePenalty()).isEqualTo(0.5);
        assertThat(options.getIncludeUsage()).isTrue();
        assertThat(options.getRetryEnabled()).isTrue();
        assertThat(options.getRetryCount()).isEqualTo(5);
        assertThat(options.getRetryDelayMs()).isEqualTo(2000);
    }

    @Test
    void shouldProvideDefaultValues() {
        ChatOptions options = ChatOptions.builder().build();

        assertThat(options.getModelOrDefault("default-model")).isEqualTo("default-model");
        assertThat(options.getTemperatureOrDefault(0.8f)).isEqualTo(0.5f); // Has default 0.5f
        assertThat(options.getRetryEnabledOrDefault(false)).isFalse();
        assertThat(options.getRetryCountOrDefault(3)).isEqualTo(3);
        assertThat(options.getRetryDelayMsOrDefault(1000)).isEqualTo(1000);
    }

    @Test
    void shouldBeImmutable() {
        Map<String, Object> extra = new HashMap<>();
        extra.put("key", "value");

        ChatOptions options = ChatOptions.builder()
                .extra(extra)
                .build();

        // Modifying original map should not affect options
        extra.put("another", "value");
        assertThat(options.getExtra()).hasSize(1);

        // Returned map should be unmodifiable
        assertThatThrownBy(() -> options.getExtra().put("new", "value"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void withMethodsShouldReturnNewInstance() {
        ChatOptions original = ChatOptions.builder()
                .model("gpt-4")
                .temperature(0.5f)
                .build();

        ChatOptions modified = original.withModel("gpt-3.5-turbo");

        assertThat(original.getModel()).isEqualTo("gpt-4");
        assertThat(modified.getModel()).isEqualTo("gpt-3.5-turbo");
        assertThat(original).isNotSameAs(modified);

        // Temperature should be preserved
        assertThat(modified.getTemperature()).isEqualTo(0.5f);
    }

    @Test
    void toBuilderShouldCopyAllValues() {
        ChatOptions original = ChatOptions.builder()
                .model("gpt-4")
                .temperature(0.7f)
                .maxTokens(1000)
                .build();

        ChatOptions copy = original.toBuilder().build();

        assertThat(copy.getModel()).isEqualTo(original.getModel());
        assertThat(copy.getTemperature()).isEqualTo(original.getTemperature());
        assertThat(copy.getMaxTokens()).isEqualTo(original.getMaxTokens());
        assertThat(copy).isEqualTo(original);
    }

    @Test
    void defaultConstantShouldBeAvailable() {
        assertThat(ChatOptions.DEFAULT).isNotNull();
        assertThat(ChatOptions.DEFAULT.getModel()).isNull();
        assertThat(ChatOptions.DEFAULT.getTemperature()).isEqualTo(0.5f);
    }

    @Test
    void shouldSupportExtraParameters() {
        ChatOptions options = ChatOptions.builder()
                .addExtra("custom_param", "value")
                .addExtra("another_param", 123)
                .build();

        assertThat(options.getExtra())
                .containsEntry("custom_param", "value")
                .containsEntry("another_param", 123);
    }

    @Test
    void equalsShouldWork() {
        ChatOptions options1 = ChatOptions.builder()
                .model("gpt-4")
                .temperature(0.7f)
                .build();

        ChatOptions options2 = ChatOptions.builder()
                .model("gpt-4")
                .temperature(0.7f)
                .build();

        ChatOptions options3 = ChatOptions.builder()
                .model("gpt-3.5-turbo")
                .temperature(0.7f)
                .build();

        assertThat(options1).isEqualTo(options2);
        assertThat(options1).isNotEqualTo(options3);
        assertThat(options1.hashCode()).isEqualTo(options2.hashCode());
    }
}
