package io.github.llmkit.message;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MessageTest {

    @Test
    void userMessageShouldHaveCorrectType() {
        UserMessage message = new UserMessage("Hello");

        assertThat(message.getContent()).isEqualTo("Hello");
        assertThat(message.getType()).isEqualTo(MessageType.USER);
        assertThat(message.getRole()).isEqualTo("user");
    }

    @Test
    void systemMessageShouldHaveCorrectType() {
        SystemMessage message = new SystemMessage("You are helpful");

        assertThat(message.getContent()).isEqualTo("You are helpful");
        assertThat(message.getType()).isEqualTo(MessageType.SYSTEM);
        assertThat(message.getRole()).isEqualTo("system");
    }

    @Test
    void aiMessageShouldHaveCorrectType() {
        AiMessage message = new AiMessage("I can help");

        assertThat(message.getContent()).isEqualTo("I can help");
        assertThat(message.getType()).isEqualTo(MessageType.ASSISTANT);
        assertThat(message.getRole()).isEqualTo("assistant");
    }

    @Test
    void staticFactoryMethodsShouldWork() {
        assertThat(UserMessage.of("Hello").getContent()).isEqualTo("Hello");
        assertThat(SystemMessage.of("System").getContent()).isEqualTo("System");
        assertThat(AiMessage.of("AI").getContent()).isEqualTo("AI");
    }

    @Test
    void messagesShouldBeEqual() {
        UserMessage msg1 = new UserMessage("Hello");
        UserMessage msg2 = new UserMessage("Hello");
        UserMessage msg3 = new UserMessage("World");

        assertThat(msg1).isEqualTo(msg2);
        assertThat(msg1).isNotEqualTo(msg3);
        assertThat(msg1.hashCode()).isEqualTo(msg2.hashCode());
    }

    @Test
    void differentTypesWithSameContentShouldNotBeEqual() {
        UserMessage user = new UserMessage("Hello");
        AiMessage ai = new AiMessage("Hello");

        assertThat(user).isNotEqualTo(ai);
    }

    @Test
    void toStringShouldTruncateLongContent() {
        String longContent = "A".repeat(100);
        UserMessage message = new UserMessage(longContent);

        String toString = message.toString();
        assertThat(toString).contains("...");
        assertThat(toString.length()).isLessThan(longContent.length());
    }
}
