package io.github.llmkit.exception;

/**
 * Exception thrown during chat operations.
 *
 * <p>This exception is thrown when an error occurs during the chat request/response
 * lifecycle, including API errors, response parsing failures, and other chat-related issues.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class ChatException extends LLMKitException {

    private final String rawResponse;

    /**
     * Creates a new ChatException with the specified message.
     *
     * @param message the detail message
     */
    public ChatException(String message) {
        this(message, null, null);
    }

    /**
     * Creates a new ChatException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public ChatException(String message, Throwable cause) {
        this(message, null, cause);
    }

    /**
     * Creates a new ChatException with the message, raw response, and cause.
     *
     * @param message     the detail message
     * @param rawResponse the raw response from the provider (if available)
     * @param cause       the cause of this exception
     */
    public ChatException(String message, String rawResponse, Throwable cause) {
        super(message, cause);
        this.rawResponse = rawResponse;
    }

    /**
     * Returns the raw response from the provider.
     *
     * @return the raw response string, or null if not available
     */
    public String getRawResponse() {
        return rawResponse;
    }
}
