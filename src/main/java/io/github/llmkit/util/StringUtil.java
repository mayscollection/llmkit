package io.github.llmkit.util;

/**
 * Utility methods for string operations.
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class StringUtil {

    private StringUtil() {
        // Prevent instantiation
    }

    /**
     * Checks if a string has text (not null, not empty, not only whitespace).
     *
     * @param str the string to check
     * @return true if the string has text
     */
    public static boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Checks if a string has no text (null, empty, or only whitespace).
     *
     * @param str the string to check
     * @return true if the string has no text
     */
    public static boolean noText(String str) {
        return !hasText(str);
    }

    /**
     * Returns the string if it has text, otherwise returns the default value.
     *
     * @param str          the string to check
     * @param defaultValue the default value
     * @return the string or default value
     */
    public static String defaultIfNoText(String str, String defaultValue) {
        return hasText(str) ? str : defaultValue;
    }

    /**
     * Truncates a string to the specified length, adding ellipsis if truncated.
     *
     * @param str       the string to truncate
     * @param maxLength the maximum length
     * @return the truncated string
     */
    public static String truncate(String str, int maxLength) {
        if (str == null || str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength) + "...";
    }
}
