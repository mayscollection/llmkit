package io.github.llmkit.exception;

/**
 * Exception thrown when parsing a response fails.
 *
 * <p>This includes JSON parsing errors, unexpected response formats,
 * and missing required fields in the response.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class ParseException extends LLMKitException {

    private final String rawContent;

    /**
     * Creates a new ParseException with the specified message.
     *
     * @param message the detail message
     */
    public ParseException(String message) {
        this(message, null, null);
    }

    /**
     * Creates a new ParseException with the specified message and raw content.
     *
     * @param message    the detail message
     * @param rawContent the raw content that failed to parse
     */
    public ParseException(String message, String rawContent) {
        this(message, rawContent, null);
    }

    /**
     * Creates a new ParseException with full details.
     *
     * @param message    the detail message
     * @param rawContent the raw content that failed to parse
     * @param cause      the cause of this exception
     */
    public ParseException(String message, String rawContent, Throwable cause) {
        super(message, cause);
        this.rawContent = rawContent;
    }

    /**
     * Returns the raw content that failed to parse.
     *
     * @return the raw content, or null if not available
     */
    public String getRawContent() {
        return rawContent;
    }

    /**
     * Creates a ParseException for invalid JSON.
     *
     * @param rawContent the invalid JSON content
     * @param cause      the parsing exception
     * @return a new ParseException
     */
    public static ParseException invalidJson(String rawContent, Throwable cause) {
        return new ParseException("Failed to parse JSON response", rawContent, cause);
    }

    /**
     * Creates a ParseException for a missing required field.
     *
     * @param fieldName  the name of the missing field
     * @param rawContent the raw response content
     * @return a new ParseException
     */
    public static ParseException missingField(String fieldName, String rawContent) {
        return new ParseException("Missing required field in response: " + fieldName, rawContent);
    }
}
