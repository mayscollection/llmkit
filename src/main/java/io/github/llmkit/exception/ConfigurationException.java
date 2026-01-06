package io.github.llmkit.exception;

/**
 * Exception thrown when there is a configuration error.
 *
 * <p>This includes missing required configuration values, invalid configuration
 * formats, or incompatible configuration combinations.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class ConfigurationException extends LLMKitException {

    /**
     * Creates a new ConfigurationException with the specified message.
     *
     * @param message the detail message
     */
    public ConfigurationException(String message) {
        super(message);
    }

    /**
     * Creates a new ConfigurationException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause   the cause of this exception
     */
    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a ConfigurationException for a missing required field.
     *
     * @param fieldName the name of the missing field
     * @return a new ConfigurationException
     */
    public static ConfigurationException missingField(String fieldName) {
        return new ConfigurationException("Required configuration field is missing: " + fieldName);
    }

    /**
     * Creates a ConfigurationException for an invalid field value.
     *
     * @param fieldName the name of the field
     * @param value     the invalid value
     * @param reason    the reason why the value is invalid
     * @return a new ConfigurationException
     */
    public static ConfigurationException invalidValue(String fieldName, Object value, String reason) {
        return new ConfigurationException(
                String.format("Invalid value for '%s': %s. Reason: %s", fieldName, value, reason));
    }
}
