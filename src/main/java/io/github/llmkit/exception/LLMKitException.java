package io.github.llmkit.exception;

/**
 * Base exception for all LLMKit exceptions.
 *
 * <p>All framework-specific exceptions extend this class, providing a consistent
 * exception hierarchy for error handling in LLM applications.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class LLMKitException extends RuntimeException {

    private final String errorCode;

    /**
     * Creates a new LLMKitException with the specified message.
     *
     * @param message the detail message
     */
    public LLMKitException(String message) {
        this(null, message, null);
    }

    /**
     * Creates a new LLMKitException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public LLMKitException(String message, Throwable cause) {
        this(null, message, cause);
    }

    /**
     * Creates a new LLMKitException with an error code, message, and cause.
     *
     * @param errorCode the error code for programmatic handling
     * @param message   the detail message
     * @param cause     the cause of this exception
     */
    public LLMKitException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Returns the error code associated with this exception.
     *
     * @return the error code, or null if not set
     */
    public String getErrorCode() {
        return errorCode;
    }
}
