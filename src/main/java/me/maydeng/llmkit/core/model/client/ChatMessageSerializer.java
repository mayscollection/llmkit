package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.message.Message;
import me.maydeng.llmkit.core.model.chat.ChatConfig;

import java.util.List;
import java.util.Map;

public interface ChatMessageSerializer {
    List<Map<String, Object>> serializeMessages(List<Message> messages, ChatConfig config);
}
