package me.maydeng.llmkit.core.model.client;

public interface StreamClientListener {
    void onStart(StreamClient client);

    void onMessage(StreamClient client, String response);

    void onStop(StreamClient client);

    void onFailure(StreamClient client, Throwable throwable);
}
