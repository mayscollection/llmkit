package io.github.llmkit.message;

/**
 * Enum representing the type of a message in a conversation.
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public enum MessageType {

    /**
     * A system message that sets the behavior or context for the AI.
     */
    SYSTEM("system"),

    /**
     * A message from the user.
     */
    USER("user"),

    /**
     * A message from the AI assistant.
     */
    ASSISTANT("assistant"),

    /**
     * A function/tool call message.
     */
    FUNCTION("function"),

    /**
     * A tool response message.
     */
    TOOL("tool");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

    /**
     * Returns the string value used in API requests.
     *
     * @return the API value
     */
    public String getValue() {
        return value;
    }

    /**
     * Parses a string value to a MessageType.
     *
     * @param value the string value
     * @return the corresponding MessageType
     * @throws IllegalArgumentException if the value is not recognized
     */
    public static MessageType fromValue(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Message type value cannot be null");
        }
        for (MessageType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown message type: " + value);
    }

    @Override
    public String toString() {
        return value;
    }
}
