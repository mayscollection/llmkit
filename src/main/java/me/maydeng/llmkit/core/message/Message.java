package me.maydeng.llmkit.core.message;

public abstract class Message {
    private String content;

    protected Message() {
    }

    protected Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
