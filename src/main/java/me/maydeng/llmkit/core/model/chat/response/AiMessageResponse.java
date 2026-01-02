package me.maydeng.llmkit.core.model.chat.response;

import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.model.chat.ChatContext;

public class AiMessageResponse extends AbstractMessageResponse<AiMessage> {
    private final ChatContext context;
    private final String rawText;
    private final AiMessage message;

    public AiMessageResponse(ChatContext context, String rawText, AiMessage message) {
        this.context = context;
        this.rawText = rawText;
        this.message = message;
    }

    public static AiMessageResponse error(ChatContext context, String rawText, String errorMessage) {
        AiMessageResponse errorResp = new AiMessageResponse(context, rawText, null);
        errorResp.setError(true);
        errorResp.setErrorMessage(errorMessage);
        return errorResp;
    }

    public ChatContext getContext() {
        return context;
    }

    public String getRawText() {
        return rawText;
    }

    @Override
    public AiMessage getMessage() {
        return message;
    }
}
