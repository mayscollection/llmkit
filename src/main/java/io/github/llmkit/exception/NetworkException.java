package io.github.llmkit.exception;

/**
 * Exception thrown when a network error occurs.
 *
 * <p>This includes connection failures, timeouts, DNS resolution failures,
 * and other network-related issues.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class NetworkException extends LLMKitException {

    private final Integer httpStatusCode;
    private final String responseBody;

    /**
     * Creates a new NetworkException with the specified message.
     *
     * @param message the detail message
     */
    public NetworkException(String message) {
        this(message, null, null, null);
    }

    /**
     * Creates a new NetworkException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public NetworkException(String message, Throwable cause) {
        this(message, null, null, cause);
    }

    /**
     * Creates a new NetworkException with HTTP status information.
     *
     * @param message        the detail message
     * @param httpStatusCode the HTTP status code
     * @param responseBody   the response body (if available)
     */
    public NetworkException(String message, Integer httpStatusCode, String responseBody) {
        this(message, httpStatusCode, responseBody, null);
    }

    /**
     * Creates a new NetworkException with full details.
     *
     * @param message        the detail message
     * @param httpStatusCode the HTTP status code
     * @param responseBody   the response body (if available)
     * @param cause          the cause of this exception
     */
    public NetworkException(String message, Integer httpStatusCode, String responseBody, Throwable cause) {
        super(message, cause);
        this.httpStatusCode = httpStatusCode;
        this.responseBody = responseBody;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return the HTTP status code, or null if not applicable
     */
    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    /**
     * Returns the response body.
     *
     * @return the response body, or null if not available
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * Checks if this is a timeout error.
     *
     * @return true if this appears to be a timeout error
     */
    public boolean isTimeout() {
        Throwable cause = getCause();
        if (cause != null) {
            String causeName = cause.getClass().getName();
            return causeName.contains("Timeout") || causeName.contains("timeout");
        }
        return getMessage() != null && getMessage().toLowerCase().contains("timeout");
    }

    /**
     * Checks if this is a connection error.
     *
     * @return true if this appears to be a connection error
     */
    public boolean isConnectionError() {
        Throwable cause = getCause();
        if (cause != null) {
            String causeName = cause.getClass().getName();
            return causeName.contains("Connect") || causeName.contains("connect");
        }
        return getMessage() != null && getMessage().toLowerCase().contains("connect");
    }
}
