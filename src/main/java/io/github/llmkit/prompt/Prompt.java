package io.github.llmkit.prompt;

import io.github.llmkit.message.Message;
import io.github.llmkit.message.SystemMessage;
import io.github.llmkit.message.UserMessage;

import java.util.List;

/**
 * Represents a prompt to be sent to an LLM.
 *
 * <p>A prompt consists of one or more messages that form the conversation context.
 * This interface provides factory methods for creating common prompt types.</p>
 *
 * <h3>Simple Usage</h3>
 * <pre>{@code
 * // Single user message
 * Prompt prompt = Prompt.of("What is Java?");
 *
 * // With system message
 * Prompt prompt = Prompt.of("You are a helpful assistant", "What is Java?");
 * }</pre>
 *
 * <h3>Multi-turn Conversation</h3>
 * <pre>{@code
 * Prompt prompt = Prompt.chat()
 *     .system("You are a coding assistant")
 *     .user("Hello!")
 *     .assistant("Hi! How can I help you today?")
 *     .user("Explain recursion")
 *     .build();
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 * @see SimplePrompt
 * @see ChatPrompt
 */
public interface Prompt {

    /**
     * Returns the list of messages in this prompt.
     *
     * @return an immutable list of messages
     */
    List<Message> getMessages();

    /**
     * Returns the number of messages in this prompt.
     *
     * @return the message count
     */
    default int size() {
        return getMessages().size();
    }

    /**
     * Checks if this prompt is empty.
     *
     * @return true if there are no messages
     */
    default boolean isEmpty() {
        return getMessages().isEmpty();
    }

    // ========== Factory Methods ==========

    /**
     * Creates a simple prompt with a single user message.
     *
     * @param userMessage the user message content
     * @return a new Prompt
     */
    static Prompt of(String userMessage) {
        return SimplePrompt.user(userMessage);
    }

    /**
     * Creates a simple prompt with a system message and user message.
     *
     * @param systemMessage the system message content
     * @param userMessage   the user message content
     * @return a new Prompt
     */
    static Prompt of(String systemMessage, String userMessage) {
        return SimplePrompt.of(systemMessage, userMessage);
    }

    /**
     * Creates a prompt from a list of messages.
     *
     * @param messages the messages
     * @return a new Prompt
     */
    static Prompt of(List<Message> messages) {
        return ChatPrompt.of(messages);
    }

    /**
     * Creates a prompt from varargs messages.
     *
     * @param messages the messages
     * @return a new Prompt
     */
    static Prompt of(Message... messages) {
        return ChatPrompt.of(messages);
    }

    /**
     * Starts building a multi-turn chat prompt.
     *
     * @return a new ChatPromptBuilder
     */
    static ChatPromptBuilder chat() {
        return new ChatPromptBuilder();
    }

    /**
     * Starts building a chat prompt with a system message.
     *
     * @param systemMessage the system message content
     * @return a new ChatPromptBuilder with the system message
     */
    static ChatPromptBuilder chat(String systemMessage) {
        return new ChatPromptBuilder().system(systemMessage);
    }
}
