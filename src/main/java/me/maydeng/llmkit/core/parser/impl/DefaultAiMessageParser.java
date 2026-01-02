package me.maydeng.llmkit.core.parser.impl;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.model.chat.ChatContext;
import me.maydeng.llmkit.core.parser.AiMessageParser;

public class DefaultAiMessageParser implements AiMessageParser<JSONObject> {
    private JSONPath contentPath;
    private JSONPath deltaContentPath;

    public static DefaultAiMessageParser getOpenAIMessageParser() {
        DefaultAiMessageParser parser = new DefaultAiMessageParser();
        parser.setContentPath(JSONPath.of("$.choices[0].message.content"));
        parser.setDeltaContentPath(JSONPath.of("$.choices[0].delta.content"));
        return parser;
    }

    public JSONPath getContentPath() {
        return contentPath;
    }

    public void setContentPath(JSONPath contentPath) {
        this.contentPath = contentPath;
    }

    public JSONPath getDeltaContentPath() {
        return deltaContentPath;
    }

    public void setDeltaContentPath(JSONPath deltaContentPath) {
        this.deltaContentPath = deltaContentPath;
    }

    @Override
    public AiMessage parse(JSONObject rootJson, ChatContext context) {
        AiMessage message = new AiMessage();
        if (context.getOptions().isStreaming()) {
            if (deltaContentPath != null) {
                message.setContent((String) deltaContentPath.eval(rootJson));
            }
        } else {
            if (contentPath != null) {
                message.setContent((String) contentPath.eval(rootJson));
            }
        }
        return message;
    }
}
