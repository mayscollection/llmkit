package me.maydeng.llmkit.core.prompt;

import me.maydeng.llmkit.core.message.Message;

import java.util.List;

public abstract class Prompt {
    public abstract List<Message> getMessages();
}
