package io.github.llmkit.exception;

/**
 * Exception thrown when the LLM provider returns an error.
 *
 * <p>This exception captures provider-specific error information such as
 * error codes, error types, and HTTP status codes.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class ProviderException extends ChatException {

    private final String providerErrorCode;
    private final String providerErrorType;
    private final Integer httpStatusCode;

    /**
     * Creates a new ProviderException with provider error details.
     *
     * @param message           the detail message
     * @param providerErrorCode the error code from the provider
     * @param providerErrorType the error type from the provider
     * @param rawResponse       the raw response from the provider
     */
    public ProviderException(String message, String providerErrorCode,
                             String providerErrorType, String rawResponse) {
        this(message, providerErrorCode, providerErrorType, null, rawResponse);
    }

    /**
     * Creates a new ProviderException with full details.
     *
     * @param message           the detail message
     * @param providerErrorCode the error code from the provider
     * @param providerErrorType the error type from the provider
     * @param httpStatusCode    the HTTP status code
     * @param rawResponse       the raw response from the provider
     */
    public ProviderException(String message, String providerErrorCode,
                             String providerErrorType, Integer httpStatusCode, String rawResponse) {
        super(message, rawResponse, null);
        this.providerErrorCode = providerErrorCode;
        this.providerErrorType = providerErrorType;
        this.httpStatusCode = httpStatusCode;
    }

    /**
     * Returns the error code from the provider.
     *
     * @return the provider error code
     */
    public String getProviderErrorCode() {
        return providerErrorCode;
    }

    /**
     * Returns the error type from the provider.
     *
     * @return the provider error type
     */
    public String getProviderErrorType() {
        return providerErrorType;
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
     * Checks if this is a rate limit error.
     *
     * @return true if this is a rate limit error
     */
    public boolean isRateLimitError() {
        return "rate_limit_exceeded".equals(providerErrorType) ||
               (httpStatusCode != null && httpStatusCode == 429);
    }

    /**
     * Checks if this is an authentication error.
     *
     * @return true if this is an authentication error
     */
    public boolean isAuthenticationError() {
        return "invalid_api_key".equals(providerErrorCode) ||
               "authentication_error".equals(providerErrorType) ||
               (httpStatusCode != null && httpStatusCode == 401);
    }
}
