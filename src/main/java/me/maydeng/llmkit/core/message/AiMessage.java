package me.maydeng.llmkit.core.message;

import me.maydeng.llmkit.core.util.StringUtil;

public class AiMessage extends Message {
    private String fullContent;
    private boolean finished;

    public AiMessage() {
    }

    public AiMessage(String content) {
        super(content);
    }

    public String getFullContent() {
        return fullContent;
    }

    public void setFullContent(String fullContent) {
        this.fullContent = fullContent;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void merge(AiMessage delta) {
        if (delta == null) {
            return;
        }
        setContent(StringUtil.append(getContent(), delta.getContent()));
    }
}
