package me.maydeng.llmkit.core.util;

import java.util.concurrent.Callable;

public final class Retryer {

    private Retryer() {
    }

    public static <T> T retry(Callable<T> callable, int retryCount, int retryDelayMs) {
        int attempts = 0;
        while (true) {
            try {
                return callable.call();
            } catch (Exception ex) {
                attempts++;
                if (attempts > retryCount) {
                    throw new RuntimeException(ex);
                }
                sleepQuietly(retryDelayMs);
            }
        }
    }

    public static void retry(Runnable runnable, int retryCount, int retryDelayMs) {
        retry(() -> {
            runnable.run();
            return null;
        }, retryCount, retryDelayMs);
    }

    private static void sleepQuietly(int delayMs) {
        if (delayMs <= 0) {
            return;
        }
        try {
            Thread.sleep(delayMs);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
