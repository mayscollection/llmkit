package me.maydeng.llmkit.core.model.client.impl;

import me.maydeng.llmkit.core.model.chat.ChatConfig;
import me.maydeng.llmkit.core.model.client.StreamClient;
import me.maydeng.llmkit.core.model.client.StreamClientListener;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

import java.util.Map;

public class SseClient extends EventSourceListener implements StreamClient {

    private final OkHttpClient okHttpClient;
    private EventSource eventSource;
    private StreamClientListener listener;
    private boolean stopped;

    public SseClient() {
        this(new OkHttpClient());
    }

    public SseClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new IllegalArgumentException("OkHttpClient must not be null");
        }
        this.okHttpClient = okHttpClient;
    }

    @Override
    public void start(String url, Map<String, String> headers, String payload, StreamClientListener listener, ChatConfig config) {
        this.listener = listener;
        this.stopped = false;

        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(payload == null ? "" : payload, mediaType);
        Request request = builder.post(body).build();

        EventSource.Factory factory = EventSources.createFactory(this.okHttpClient);
        this.eventSource = factory.newEventSource(request, this);

        if (this.listener != null) {
            this.listener.onStart(this);
        }
    }

    @Override
    public void stop() {
        tryToStop();
    }

    @Override
    public void onClosed(EventSource eventSource) {
        tryToStop();
    }

    @Override
    public void onEvent(EventSource eventSource, String id, String type, String data) {
        if (listener != null) {
            listener.onMessage(this, data);
        }
    }

    @Override
    public void onFailure(EventSource eventSource, Throwable t, Response response) {
        Throwable throwable = t;
        if (throwable == null && response != null) {
            throwable = new RuntimeException("SSE failure with status " + response.code());
        }
        if (listener != null) {
            listener.onFailure(this, throwable);
        }
        tryToStop();
    }

    private void tryToStop() {
        if (!stopped) {
            stopped = true;
            if (listener != null) {
                listener.onStop(this);
            }
            if (eventSource != null) {
                eventSource.cancel();
                eventSource = null;
            }
        }
    }
}
