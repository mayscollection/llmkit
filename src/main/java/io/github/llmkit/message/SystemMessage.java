package io.github.llmkit.message;

/**
 * A system message that sets the behavior or context for the AI.
 *
 * <p>System messages typically contain instructions that define how the AI should
 * behave, its persona, or constraints on its responses.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 * SystemMessage system = SystemMessage.of("You are a helpful coding assistant.");
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class SystemMessage extends Message {

    /**
     * Creates a new system message with the specified content.
     *
     * @param content the system message content
     */
    public SystemMessage(String content) {
        super(content);
    }

    /**
     * Creates a new system message.
     *
     * @param content the system message content
     * @return a new SystemMessage
     */
    public static SystemMessage of(String content) {
        return new SystemMessage(content);
    }

    @Override
    public MessageType getType() {
        return MessageType.SYSTEM;
    }
}
