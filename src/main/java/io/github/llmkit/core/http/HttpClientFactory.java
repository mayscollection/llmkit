package io.github.llmkit.core.http;

import okhttp3.OkHttpClient;

import java.util.concurrent.TimeUnit;

/**
 * Factory for creating and sharing OkHttpClient instances.
 *
 * <p>This factory implements a singleton pattern for the default client to ensure
 * efficient reuse of connections and resources across the application.</p>
 *
 * <h3>Usage</h3>
 * <pre>{@code
 * // Get the shared default client
 * OkHttpClient client = HttpClientFactory.getDefaultClient();
 *
 * // Create a custom client with different timeouts
 * OkHttpClient customClient = HttpClientFactory.createClient(60, 120, 60);
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public final class HttpClientFactory {

    private static volatile OkHttpClient defaultClient;
    private static final Object LOCK = new Object();

    private static final int DEFAULT_CONNECT_TIMEOUT_SECONDS = 30;
    private static final int DEFAULT_READ_TIMEOUT_SECONDS = 60;
    private static final int DEFAULT_WRITE_TIMEOUT_SECONDS = 30;

    private HttpClientFactory() {
        // Prevent instantiation
    }

    /**
     * Returns the shared default OkHttpClient instance.
     *
     * <p>This method is thread-safe and uses double-checked locking for
     * lazy initialization.</p>
     *
     * @return the shared OkHttpClient instance
     */
    public static OkHttpClient getDefaultClient() {
        if (defaultClient == null) {
            synchronized (LOCK) {
                if (defaultClient == null) {
                    defaultClient = createDefaultClient();
                }
            }
        }
        return defaultClient;
    }

    /**
     * Creates a new OkHttpClient with default settings.
     *
     * <p>Default settings:</p>
     * <ul>
     *   <li>Connect timeout: 30 seconds</li>
     *   <li>Read timeout: 60 seconds</li>
     *   <li>Write timeout: 30 seconds</li>
     *   <li>Retry on connection failure: enabled</li>
     * </ul>
     *
     * @return a new OkHttpClient instance
     */
    public static OkHttpClient createDefaultClient() {
        return new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
    }

    /**
     * Creates a new OkHttpClient with custom timeouts.
     *
     * <p>The new client shares the connection pool with the default client
     * for efficient resource usage.</p>
     *
     * @param connectTimeoutSeconds the connect timeout in seconds
     * @param readTimeoutSeconds    the read timeout in seconds
     * @param writeTimeoutSeconds   the write timeout in seconds
     * @return a new OkHttpClient instance
     */
    public static OkHttpClient createClient(long connectTimeoutSeconds,
                                            long readTimeoutSeconds,
                                            long writeTimeoutSeconds) {
        return getDefaultClient().newBuilder()
                .connectTimeout(connectTimeoutSeconds, TimeUnit.SECONDS)
                .readTimeout(readTimeoutSeconds, TimeUnit.SECONDS)
                .writeTimeout(writeTimeoutSeconds, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Creates a new OkHttpClient builder based on the default client.
     *
     * <p>Use this method when you need more customization options.</p>
     *
     * @return a new builder based on the default client
     */
    public static OkHttpClient.Builder newBuilder() {
        return getDefaultClient().newBuilder();
    }

    /**
     * Sets a custom default client.
     *
     * <p>This method should be called before any other usage of the factory
     * if you need to customize the default client settings.</p>
     *
     * @param client the client to use as default
     * @throws IllegalArgumentException if client is null
     */
    public static void setDefaultClient(OkHttpClient client) {
        if (client == null) {
            throw new IllegalArgumentException("Client must not be null");
        }
        synchronized (LOCK) {
            defaultClient = client;
        }
    }

    /**
     * Shuts down the default client and releases resources.
     *
     * <p>Call this method on application shutdown if you want to release
     * all resources held by the HTTP client.</p>
     */
    public static void shutdown() {
        synchronized (LOCK) {
            if (defaultClient != null) {
                defaultClient.dispatcher().executorService().shutdown();
                defaultClient.connectionPool().evictAll();
                if (defaultClient.cache() != null) {
                    try {
                        defaultClient.cache().close();
                    } catch (Exception ignored) {
                        // Ignore cache close errors
                    }
                }
                defaultClient = null;
            }
        }
    }

    /**
     * Resets the factory to its initial state.
     *
     * <p>This is primarily intended for testing purposes.</p>
     */
    public static void reset() {
        shutdown();
    }
}
