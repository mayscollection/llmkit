package me.maydeng.llmkit.core.model.client;

import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.message.Message;
import me.maydeng.llmkit.core.message.SystemMessage;
import me.maydeng.llmkit.core.message.UserMessage;
import me.maydeng.llmkit.core.model.chat.ChatConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OpenAIChatMessageSerializer implements ChatMessageSerializer {

    @Override
    public List<Map<String, Object>> serializeMessages(List<Message> messages, ChatConfig config) {
        if (messages == null || messages.isEmpty()) {
            return null;
        }

        List<Map<String, Object>> messageList = new ArrayList<>(messages.size());
        for (Message message : messages) {
            Map<String, Object> objectMap = new HashMap<>(2);
            if (message instanceof UserMessage) {
                objectMap.put("role", "user");
                objectMap.put("content", message.getContent());
            } else if (message instanceof SystemMessage) {
                objectMap.put("role", "system");
                objectMap.put("content", message.getContent());
            } else if (message instanceof AiMessage) {
                objectMap.put("role", "assistant");
                objectMap.put("content", message.getContent());
            }
            messageList.add(objectMap);
        }
        return messageList;
    }
}
