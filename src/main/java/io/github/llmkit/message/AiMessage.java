package io.github.llmkit.message;

/**
 * A message from the AI assistant.
 *
 * <p>AI messages represent responses from the language model. They can also be
 * used to provide example responses or to prefill the assistant's response.</p>
 *
 * <p>Example:</p>
 * <pre>{@code
 * AiMessage ai = AiMessage.of("I'd be happy to help you with that!");
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class AiMessage extends Message {

    /**
     * Creates a new AI message with the specified content.
     *
     * @param content the AI message content
     */
    public AiMessage(String content) {
        super(content);
    }

    /**
     * Creates a new AI message.
     *
     * @param content the AI message content
     * @return a new AiMessage
     */
    public static AiMessage of(String content) {
        return new AiMessage(content);
    }

    @Override
    public MessageType getType() {
        return MessageType.ASSISTANT;
    }
}
