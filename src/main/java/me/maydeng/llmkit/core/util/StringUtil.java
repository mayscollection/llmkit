package me.maydeng.llmkit.core.util;

public final class StringUtil {

    private StringUtil() {
    }

    public static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean noText(String value) {
        return !hasText(value);
    }

    public static String append(String base, String delta) {
        if (delta == null || delta.isEmpty()) {
            return base;
        }
        if (base == null || base.isEmpty()) {
            return delta;
        }
        return base + delta;
    }
}
