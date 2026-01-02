package me.maydeng.llmkit.core.prompt;

import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.message.Message;
import me.maydeng.llmkit.core.message.SystemMessage;
import me.maydeng.llmkit.core.message.UserMessage;

import java.util.ArrayList;
import java.util.List;

public class SimplePrompt extends Prompt {
    private SystemMessage systemMessage;
    private UserMessage userMessage;
    private AiMessage aiMessage;

    public SimplePrompt() {
        this.userMessage = new UserMessage();
    }

    public SimplePrompt(String content) {
        this.userMessage = new UserMessage(content);
    }

    public SystemMessage getSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(SystemMessage systemMessage) {
        this.systemMessage = systemMessage;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public AiMessage getAiMessage() {
        return aiMessage;
    }

    public void setAiMessage(AiMessage aiMessage) {
        this.aiMessage = aiMessage;
    }

    @Override
    public List<Message> getMessages() {
        List<Message> messages = new ArrayList<>(2);
        if (systemMessage != null) {
            messages.add(systemMessage);
        }
        if (userMessage != null) {
            messages.add(userMessage);
        }
        if (aiMessage != null) {
            messages.add(aiMessage);
        }
        return messages;
    }
}
