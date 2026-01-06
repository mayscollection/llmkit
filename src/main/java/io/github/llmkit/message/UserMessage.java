package io.github.llmkit.message;

/**
 * A message from the user.
 *
 * <p>User messages represent input from the human user in a conversation.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 * UserMessage user = UserMessage.of("What is the weather today?");
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class UserMessage extends Message {

    /**
     * Creates a new user message with the specified content.
     *
     * @param content the user message content
     */
    public UserMessage(String content) {
        super(content);
    }

    /**
     * Creates a new user message.
     *
     * @param content the user message content
     * @return a new UserMessage
     */
    public static UserMessage of(String content) {
        return new UserMessage(content);
    }

    @Override
    public MessageType getType() {
        return MessageType.USER;
    }
}
