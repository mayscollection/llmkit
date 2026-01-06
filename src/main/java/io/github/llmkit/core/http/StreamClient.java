package io.github.llmkit.core.http;

import java.util.Map;

/**
 * Interface for streaming HTTP clients.
 *
 * <p>Implementations of this interface handle Server-Sent Events (SSE)
 * or other streaming protocols for real-time response streaming.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public interface StreamClient {

    /**
     * Starts a streaming connection.
     *
     * @param url      the request URL
     * @param headers  the request headers
     * @param payload  the request payload
     * @param listener the listener for stream events
     */
    void start(String url, Map<String, String> headers, String payload, StreamListener listener);

    /**
     * Stops the streaming connection.
     */
    void stop();

    /**
     * Checks if the stream is currently active.
     *
     * @return true if streaming is active
     */
    boolean isActive();

    /**
     * Listener interface for stream events.
     */
    interface StreamListener {

        /**
         * Called when the stream connection is established.
         *
         * @param client the stream client
         */
        default void onStart(StreamClient client) {
        }

        /**
         * Called when a message is received.
         *
         * @param client the stream client
         * @param data   the message data
         */
        void onMessage(StreamClient client, String data);

        /**
         * Called when the stream encounters an error.
         *
         * @param client    the stream client
         * @param throwable the error
         */
        default void onError(StreamClient client, Throwable throwable) {
        }

        /**
         * Called when the stream is closed.
         *
         * @param client the stream client
         */
        default void onClose(StreamClient client) {
        }
    }
}
