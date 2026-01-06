package io.github.llmkit.util;

import io.github.llmkit.exception.LLMKitException;

import java.util.concurrent.Callable;

/**
 * Utility for retrying operations with configurable retry count and delay.
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class Retryer {

    private Retryer() {
        // Prevent instantiation
    }

    /**
     * Retries a callable operation with the specified retry count and delay.
     *
     * @param callable     the operation to retry
     * @param retryCount   the maximum number of retries
     * @param retryDelayMs the delay between retries in milliseconds
     * @param <T>          the return type
     * @return the result of the callable
     * @throws LLMKitException if all retries fail
     */
    public static <T> T retry(Callable<T> callable, int retryCount, int retryDelayMs) {
        int attempts = 0;
        Exception lastException = null;

        while (attempts <= retryCount) {
            try {
                return callable.call();
            } catch (Exception ex) {
                lastException = ex;
                attempts++;

                if (attempts > retryCount) {
                    break;
                }

                sleepQuietly(retryDelayMs);
            }
        }

        if (lastException instanceof LLMKitException) {
            throw (LLMKitException) lastException;
        }
        throw new LLMKitException("Operation failed after " + retryCount + " retries", lastException);
    }

    /**
     * Retries a runnable operation with the specified retry count and delay.
     *
     * @param runnable     the operation to retry
     * @param retryCount   the maximum number of retries
     * @param retryDelayMs the delay between retries in milliseconds
     * @throws LLMKitException if all retries fail
     */
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
