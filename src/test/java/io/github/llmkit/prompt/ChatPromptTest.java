package io.github.llmkit.prompt;

import io.github.llmkit.message.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ChatPromptTest {

    @Test
    void shouldBuildMultiTurnConversation() {
        ChatPrompt prompt = Prompt.chat()
                .system("You are helpful")
                .user("Hello")
                .assistant("Hi there!")
                .user("How are you?")
                .build();

        assertThat(prompt.getMessages()).hasSize(4);
        assertThat(prompt.getMessages().get(0)).isInstanceOf(SystemMessage.class);
        assertThat(prompt.getMessages().get(1)).isInstanceOf(UserMessage.class);
        assertThat(prompt.getMessages().get(2)).isInstanceOf(AiMessage.class);
        assertThat(prompt.getMessages().get(3)).isInstanceOf(UserMessage.class);
    }

    @Test
    void shouldBeImmutable() {
        ChatPrompt prompt = Prompt.chat()
                .user("Hello")
                .build();

        assertThatThrownBy(() -> prompt.getMessages().add(new UserMessage("test")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void addUserMessageShouldReturnNewInstance() {
        ChatPrompt original = Prompt.chat()
                .user("Hello")
                .build();

        ChatPrompt extended = original.addUserMessage("World");

        assertThat(original.size()).isEqualTo(1);
        assertThat(extended.size()).isEqualTo(2);
        assertThat(original).isNotSameAs(extended);
    }

    @Test
    void addAssistantMessageShouldReturnNewInstance() {
        ChatPrompt original = Prompt.chat()
                .user("Hello")
                .build();

        ChatPrompt extended = original.addAssistantMessage("Hi!");

        assertThat(original.size()).isEqualTo(1);
        assertThat(extended.size()).isEqualTo(2);
        assertThat(extended.getMessages().get(1)).isInstanceOf(AiMessage.class);
    }

    @Test
    void shouldCreateFromList() {
        List<Message> messages = Arrays.asList(
                new SystemMessage("System"),
                new UserMessage("User"),
                new AiMessage("Assistant")
        );

        ChatPrompt prompt = ChatPrompt.of(messages);

        assertThat(prompt.getMessages()).hasSize(3);
    }

    @Test
    void shouldCreateFromVarargs() {
        ChatPrompt prompt = ChatPrompt.of(
                new SystemMessage("System"),
                new UserMessage("User")
        );

        assertThat(prompt.getMessages()).hasSize(2);
    }

    @Test
    void shouldGetSystemMessage() {
        ChatPrompt prompt = Prompt.chat()
                .system("System instruction")
                .user("Hello")
                .build();

        assertThat(prompt.getSystemMessage()).isNotNull();
        assertThat(prompt.getSystemMessage().getContent()).isEqualTo("System instruction");
    }

    @Test
    void shouldReturnNullSystemMessageWhenNotPresent() {
        ChatPrompt prompt = Prompt.chat()
                .user("Hello")
                .build();

        assertThat(prompt.getSystemMessage()).isNull();
    }

    @Test
    void shouldGetLastMessage() {
        ChatPrompt prompt = Prompt.chat()
                .user("Hello")
                .assistant("Hi!")
                .build();

        assertThat(prompt.getLastMessage()).isInstanceOf(AiMessage.class);
        assertThat(prompt.getLastMessage().getContent()).isEqualTo("Hi!");
    }

    @Test
    void shouldGetLastUserMessage() {
        ChatPrompt prompt = Prompt.chat()
                .user("First")
                .assistant("Response")
                .user("Second")
                .assistant("Another response")
                .build();

        assertThat(prompt.getLastUserMessage()).isNotNull();
        assertThat(prompt.getLastUserMessage().getContent()).isEqualTo("Second");
    }

    @Test
    void toBuilderShouldPreserveMessages() {
        ChatPrompt original = Prompt.chat()
                .system("System")
                .user("Hello")
                .build();

        ChatPrompt rebuilt = original.toBuilder()
                .assistant("Hi!")
                .build();

        assertThat(rebuilt.getMessages()).hasSize(3);
        assertThat(original.getMessages()).hasSize(2);
    }

    @Test
    void emptyPromptShouldWork() {
        ChatPrompt empty = ChatPrompt.empty();

        assertThat(empty.isEmpty()).isTrue();
        assertThat(empty.size()).isZero();
        assertThat(empty.getLastMessage()).isNull();
    }

    @Test
    void factoryMethodsShouldWork() {
        Prompt simple = Prompt.of("Hello");
        assertThat(simple.getMessages()).hasSize(1);
        assertThat(simple.getMessages().get(0)).isInstanceOf(UserMessage.class);

        Prompt withSystem = Prompt.of("System", "User");
        assertThat(withSystem.getMessages()).hasSize(2);
        assertThat(withSystem.getMessages().get(0)).isInstanceOf(SystemMessage.class);
    }
}
