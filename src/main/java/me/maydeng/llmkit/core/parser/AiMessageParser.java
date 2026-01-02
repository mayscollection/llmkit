package me.maydeng.llmkit.core.parser;

import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.model.chat.ChatContext;

public interface AiMessageParser<T> {
    AiMessage parse(T root, ChatContext context);
}
