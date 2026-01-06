package io.github.llmkit.prompt;

import io.github.llmkit.message.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple prompt for single-turn conversations.
 *
 * <p>This class supports a single system message, a single user message,
 * and optionally a single AI message (for prefilling). For multi-turn
 * conversations, use {@link ChatPrompt} instead.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 * // User message only
 * SimplePrompt prompt = SimplePrompt.user("Hello!");
 *
 * // System + user
 * SimplePrompt prompt = SimplePrompt.of("You are helpful", "Hello!");
 *
 * // Using builder
 * SimplePrompt prompt = SimplePrompt.builder()
 *     .system("You are helpful")
 *     .user("Hello!")
 *     .build();
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class SimplePrompt implements Prompt {

    private final SystemMessage systemMessage;
    private final UserMessage userMessage;
    private final AiMessage aiMessage;
    private final List<Message> messages;

    private SimplePrompt(SystemMessage systemMessage, UserMessage userMessage, AiMessage aiMessage) {
        this.systemMessage = systemMessage;
        this.userMessage = userMessage;
        this.aiMessage = aiMessage;

        List<Message> list = new ArrayList<>(3);
        if (systemMessage != null) {
            list.add(systemMessage);
        }
        if (userMessage != null) {
            list.add(userMessage);
        }
        if (aiMessage != null) {
            list.add(aiMessage);
        }
        this.messages = Collections.unmodifiableList(list);
    }

    /**
     * Creates a simple prompt with only a user message.
     *
     * @param content the user message content
     * @return a new SimplePrompt
     */
    public static SimplePrompt user(String content) {
        return new SimplePrompt(null, new UserMessage(content), null);
    }

    /**
     * Creates a simple prompt with system and user messages.
     *
     * @param systemContent the system message content
     * @param userContent   the user message content
     * @return a new SimplePrompt
     */
    public static SimplePrompt of(String systemContent, String userContent) {
        return new SimplePrompt(
                new SystemMessage(systemContent),
                new UserMessage(userContent),
                null
        );
    }

    /**
     * Creates a new builder for SimplePrompt.
     *
     * @return a new builder
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Returns the system message.
     *
     * @return the system message, or null if not set
     */
    public SystemMessage getSystemMessage() {
        return systemMessage;
    }

    /**
     * Returns the user message.
     *
     * @return the user message, or null if not set
     */
    public UserMessage getUserMessage() {
        return userMessage;
    }

    /**
     * Returns the AI message (for prefilling).
     *
     * @return the AI message, or null if not set
     */
    public AiMessage getAiMessage() {
        return aiMessage;
    }

    /**
     * Converts this prompt to a ChatPrompt for continuing the conversation.
     *
     * @return a new ChatPrompt with the same messages
     */
    public ChatPrompt toChatPrompt() {
        return ChatPrompt.of(messages);
    }

    @Override
    public String toString() {
        return "SimplePrompt{" +
                "systemMessage=" + (systemMessage != null ? "'" + truncate(systemMessage.getContent()) + "'" : "null") +
                ", userMessage=" + (userMessage != null ? "'" + truncate(userMessage.getContent()) + "'" : "null") +
                ", aiMessage=" + (aiMessage != null ? "'" + truncate(aiMessage.getContent()) + "'" : "null") +
                '}';
    }

    private String truncate(String s) {
        if (s == null) return null;
        return s.length() > 30 ? s.substring(0, 30) + "..." : s;
    }

    /**
     * Builder for creating {@link SimplePrompt} instances.
     */
    public static final class Builder {
        private SystemMessage systemMessage;
        private UserMessage userMessage;
        private AiMessage aiMessage;

        private Builder() {
        }

        /**
         * Sets the system message.
         *
         * @param content the system message content
         * @return this builder
         */
        public Builder system(String content) {
            this.systemMessage = content != null ? new SystemMessage(content) : null;
            return this;
        }

        /**
         * Sets the user message.
         *
         * @param content the user message content
         * @return this builder
         */
        public Builder user(String content) {
            this.userMessage = content != null ? new UserMessage(content) : null;
            return this;
        }

        /**
         * Sets the AI message (for prefilling).
         *
         * @param content the AI message content
         * @return this builder
         */
        public Builder assistant(String content) {
            this.aiMessage = content != null ? new AiMessage(content) : null;
            return this;
        }

        /**
         * Builds the SimplePrompt.
         *
         * @return a new SimplePrompt
         */
        public SimplePrompt build() {
            return new SimplePrompt(systemMessage, userMessage, aiMessage);
        }
    }
}
