package io.github.llmkit.prompt;

import io.github.llmkit.message.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Fluent builder for creating multi-turn chat prompts.
 *
 * <p>This builder provides a convenient way to construct complex conversation
 * histories with multiple messages.</p>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * ChatPrompt prompt = new ChatPromptBuilder()
 *     .system("You are a helpful assistant")
 *     .user("Hello!")
 *     .assistant("Hi! How can I help you today?")
 *     .user("What's the weather like?")
 *     .build();
 * }</pre>
 *
 * <p>Or using the factory method:</p>
 * <pre>{@code
 * ChatPrompt prompt = Prompt.chat()
 *     .system("You are helpful")
 *     .user("Hello")
 *     .build();
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class ChatPromptBuilder {

    private final List<Message> messages = new ArrayList<>();

    /**
     * Creates a new ChatPromptBuilder.
     */
    public ChatPromptBuilder() {
    }

    /**
     * Adds a system message.
     *
     * @param content the system message content
     * @return this builder
     */
    public ChatPromptBuilder system(String content) {
        if (content != null) {
            messages.add(new SystemMessage(content));
        }
        return this;
    }

    /**
     * Adds a user message.
     *
     * @param content the user message content
     * @return this builder
     */
    public ChatPromptBuilder user(String content) {
        if (content != null) {
            messages.add(new UserMessage(content));
        }
        return this;
    }

    /**
     * Adds an assistant (AI) message.
     *
     * @param content the assistant message content
     * @return this builder
     */
    public ChatPromptBuilder assistant(String content) {
        if (content != null) {
            messages.add(new AiMessage(content));
        }
        return this;
    }

    /**
     * Adds a message of any type.
     *
     * @param message the message to add
     * @return this builder
     */
    public ChatPromptBuilder add(Message message) {
        if (message != null) {
            messages.add(message);
        }
        return this;
    }

    /**
     * Adds multiple messages.
     *
     * @param messages the messages to add
     * @return this builder
     */
    public ChatPromptBuilder addAll(List<Message> messages) {
        if (messages != null) {
            this.messages.addAll(messages);
        }
        return this;
    }

    /**
     * Adds messages from an existing prompt.
     *
     * @param prompt the prompt to copy messages from
     * @return this builder
     */
    public ChatPromptBuilder from(Prompt prompt) {
        if (prompt != null) {
            this.messages.addAll(prompt.getMessages());
        }
        return this;
    }

    /**
     * Adds a conversation turn (user message followed by assistant response).
     *
     * @param userContent      the user message
     * @param assistantContent the assistant response
     * @return this builder
     */
    public ChatPromptBuilder turn(String userContent, String assistantContent) {
        user(userContent);
        assistant(assistantContent);
        return this;
    }

    /**
     * Returns the current number of messages.
     *
     * @return the message count
     */
    public int size() {
        return messages.size();
    }

    /**
     * Checks if the builder has no messages.
     *
     * @return true if empty
     */
    public boolean isEmpty() {
        return messages.isEmpty();
    }

    /**
     * Clears all messages from the builder.
     *
     * @return this builder
     */
    public ChatPromptBuilder clear() {
        messages.clear();
        return this;
    }

    /**
     * Builds the ChatPrompt.
     *
     * @return a new ChatPrompt with the accumulated messages
     */
    public ChatPrompt build() {
        return ChatPrompt.of(messages);
    }
}
