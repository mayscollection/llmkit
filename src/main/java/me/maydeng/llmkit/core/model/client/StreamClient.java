package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.model.chat.ChatConfig;

import java.util.Map;

public interface StreamClient {
    void start(String url, Map<String, String> headers, String payload, StreamClientListener listener, ChatConfig config);

    void stop();
}
