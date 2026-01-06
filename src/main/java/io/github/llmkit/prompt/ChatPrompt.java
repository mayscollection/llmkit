package io.github.llmkit.prompt;

import io.github.llmkit.message.*;

import java.util.*;

/**
 * A prompt for multi-turn conversations.
 *
 * <p>ChatPrompt supports arbitrary sequences of messages, enabling complex
 * multi-turn conversations with history. The message list is immutable.</p>
 *
 * <h3>Example Usage</h3>
 * <pre>{@code
 * // Using the builder
 * ChatPrompt prompt = Prompt.chat()
 *     .system("You are a helpful coding assistant")
 *     .user("What is Java?")
 *     .assistant("Java is a popular programming language...")
 *     .user("Show me a Hello World example")
 *     .build();
 *
 * // From a list of messages
 * ChatPrompt prompt = ChatPrompt.of(messageList);
 *
 * // Continuing a conversation
 * ChatPrompt continued = prompt
 *     .addAssistantMessage("Here's the code...")
 *     .addUserMessage("How do I compile it?");
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class ChatPrompt implements Prompt {

    private final List<Message> messages;

    private ChatPrompt(List<Message> messages) {
        this.messages = Collections.unmodifiableList(new ArrayList<>(messages));
    }

    /**
     * Creates a ChatPrompt from a list of messages.
     *
     * @param messages the messages
     * @return a new ChatPrompt
     */
    public static ChatPrompt of(List<Message> messages) {
        if (messages == null) {
            return new ChatPrompt(Collections.emptyList());
        }
        return new ChatPrompt(messages);
    }

    /**
     * Creates a ChatPrompt from varargs messages.
     *
     * @param messages the messages
     * @return a new ChatPrompt
     */
    public static ChatPrompt of(Message... messages) {
        if (messages == null || messages.length == 0) {
            return new ChatPrompt(Collections.emptyList());
        }
        return new ChatPrompt(Arrays.asList(messages));
    }

    /**
     * Creates an empty ChatPrompt.
     *
     * @return an empty ChatPrompt
     */
    public static ChatPrompt empty() {
        return new ChatPrompt(Collections.emptyList());
    }

    @Override
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Returns a new ChatPrompt with an additional user message.
     *
     * @param content the user message content
     * @return a new ChatPrompt with the added message
     */
    public ChatPrompt addUserMessage(String content) {
        List<Message> newMessages = new ArrayList<>(messages);
        newMessages.add(new UserMessage(content));
        return new ChatPrompt(newMessages);
    }

    /**
     * Returns a new ChatPrompt with an additional assistant message.
     *
     * @param content the assistant message content
     * @return a new ChatPrompt with the added message
     */
    public ChatPrompt addAssistantMessage(String content) {
        List<Message> newMessages = new ArrayList<>(messages);
        newMessages.add(new AiMessage(content));
        return new ChatPrompt(newMessages);
    }

    /**
     * Returns a new ChatPrompt with an additional message.
     *
     * @param message the message to add
     * @return a new ChatPrompt with the added message
     */
    public ChatPrompt addMessage(Message message) {
        if (message == null) {
            return this;
        }
        List<Message> newMessages = new ArrayList<>(messages);
        newMessages.add(message);
        return new ChatPrompt(newMessages);
    }

    /**
     * Returns a new ChatPrompt with additional messages.
     *
     * @param additionalMessages the messages to add
     * @return a new ChatPrompt with the added messages
     */
    public ChatPrompt addMessages(List<Message> additionalMessages) {
        if (additionalMessages == null || additionalMessages.isEmpty()) {
            return this;
        }
        List<Message> newMessages = new ArrayList<>(messages);
        newMessages.addAll(additionalMessages);
        return new ChatPrompt(newMessages);
    }

    /**
     * Converts this prompt to a builder for modification.
     *
     * @return a new ChatPromptBuilder with this prompt's messages
     */
    public ChatPromptBuilder toBuilder() {
        ChatPromptBuilder builder = new ChatPromptBuilder();
        for (Message msg : messages) {
            builder.add(msg);
        }
        return builder;
    }

    /**
     * Returns the system message if present and first.
     *
     * @return the system message, or null if not present
     */
    public SystemMessage getSystemMessage() {
        if (!messages.isEmpty() && messages.get(0) instanceof SystemMessage) {
            return (SystemMessage) messages.get(0);
        }
        return null;
    }

    /**
     * Returns the last message in the prompt.
     *
     * @return the last message, or null if empty
     */
    public Message getLastMessage() {
        return messages.isEmpty() ? null : messages.get(messages.size() - 1);
    }

    /**
     * Returns the last user message in the prompt.
     *
     * @return the last user message, or null if none
     */
    public UserMessage getLastUserMessage() {
        for (int i = messages.size() - 1; i >= 0; i--) {
            if (messages.get(i) instanceof UserMessage) {
                return (UserMessage) messages.get(i);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "ChatPrompt{" +
                "messageCount=" + messages.size() +
                ", messages=" + formatMessages() +
                '}';
    }

    private String formatMessages() {
        if (messages.isEmpty()) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder("[");
        int limit = Math.min(messages.size(), 3);
        for (int i = 0; i < limit; i++) {
            if (i > 0) sb.append(", ");
            Message msg = messages.get(i);
            sb.append(msg.getType().getValue())
              .append(": '")
              .append(truncate(msg.getContent(), 20))
              .append("'");
        }
        if (messages.size() > 3) {
            sb.append(", ... ").append(messages.size() - 3).append(" more");
        }
        sb.append("]");
        return sb.toString();
    }

    private String truncate(String s, int maxLen) {
        if (s == null) return "null";
        return s.length() > maxLen ? s.substring(0, maxLen) + "..." : s;
    }
}
