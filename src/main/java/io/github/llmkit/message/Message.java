package io.github.llmkit.message;

import java.util.Objects;

/**
 * Abstract base class for all message types in a conversation.
 *
 * <p>Messages are immutable and represent a single turn in a conversation.
 * Subclasses include {@link UserMessage}, {@link SystemMessage}, and {@link AiMessage}.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public abstract class Message {

    private final String content;

    /**
     * Creates a new message with the specified content.
     *
     * @param content the message content
     */
    protected Message(String content) {
        this.content = content;
    }

    /**
     * Returns the content of this message.
     *
     * @return the message content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the type of this message.
     *
     * @return the message type
     */
    public abstract MessageType getType();

    /**
     * Returns the role string used in API requests.
     *
     * @return the role string
     */
    public String getRole() {
        return getType().getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(content, message.content) &&
                getType() == message.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, getType());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "content='" + (content != null && content.length() > 50
                        ? content.substring(0, 50) + "..."
                        : content) + '\'' +
                '}';
    }
}
