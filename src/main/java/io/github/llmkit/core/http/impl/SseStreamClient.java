package io.github.llmkit.core.http.impl;

import io.github.llmkit.core.http.HttpClientFactory;
import io.github.llmkit.core.http.StreamClient;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import java.util.Map;

/**
 * Server-Sent Events (SSE) implementation of StreamClient.
 *
 * <p>This client handles streaming responses from LLM APIs that use the
 * SSE protocol for real-time response delivery.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class SseStreamClient extends EventSourceListener implements StreamClient {

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient okHttpClient;
    private EventSource eventSource;
    private StreamListener listener;
    private volatile boolean active;

    /**
     * Creates an SseStreamClient using the shared default OkHttpClient.
     */
    public SseStreamClient() {
        this(HttpClientFactory.getDefaultClient());
    }

    /**
     * Creates an SseStreamClient with a custom OkHttpClient.
     *
     * @param okHttpClient the OkHttpClient to use
     * @throws IllegalArgumentException if okHttpClient is null
     */
    public SseStreamClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new IllegalArgumentException("OkHttpClient must not be null");
        }
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void start(String url, Map<String, String> headers, String payload, StreamListener listener) {
        this.listener = listener;
        this.active = true;

        Request.Builder builder = new Request.Builder().url(url);

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }

        RequestBody body = RequestBody.create(payload == null ? "" : payload, JSON_TYPE);
        Request request = builder.post(body).build();

        EventSource.Factory factory = EventSources.createFactory(okHttpClient);
        this.eventSource = factory.newEventSource(request, this);

        if (listener != null) {
            listener.onStart(this);
        }
    }

    @Override
    public void stop() {
        doStop();
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void onOpen(EventSource eventSource, Response response) {
        // Connection opened, already notified in start()
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        if (listener != null && active) {
            listener.onMessage(this, data);
        }
    }

    @Override
    public void onClosed(EventSource eventSource) {
        doStop();
    }

    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        Throwable error = t;
        if (error == null && response != null) {
            error = new RuntimeException("SSE failure with status " + response.code());
        }

        if (listener != null) {
            listener.onError(this, error);
        }

        doStop();
    }

    private void doStop() {
        if (active) {
            active = false;

            if (listener != null) {
                listener.onClose(this);
            }

            if (eventSource != null) {
                eventSource.cancel();
                eventSource = null;
            }
        }
    }
}
