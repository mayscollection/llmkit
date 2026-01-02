package me.maydeng.llmkit.core.model.client;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.model.chat.ChatContext;
import me.maydeng.llmkit.core.model.chat.ChatModel;
import me.maydeng.llmkit.core.model.chat.StreamResponseListener;
import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;
import me.maydeng.llmkit.core.parser.AiMessageParser;
import me.maydeng.llmkit.core.util.StringUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class BaseStreamClientListener implements StreamClientListener {

    private final StreamResponseListener streamResponseListener;
    private final ChatContext chatContext;
    private final AiMessageParser<JSONObject> messageParser;
    private final StreamContext context;
    private final AiMessage fullMessage = new AiMessage();
    private final AtomicBoolean finishedFlag = new AtomicBoolean(false);
    private final AtomicBoolean stoppedFlag = new AtomicBoolean(false);

    public BaseStreamClientListener(
            ChatModel chatModel,
            ChatContext chatContext,
            StreamClient client,
            StreamResponseListener streamResponseListener,
            AiMessageParser<JSONObject> messageParser) {
        this.streamResponseListener = streamResponseListener;
        this.chatContext = chatContext;
        this.messageParser = messageParser;
        this.context = new StreamContext(chatModel, chatContext, client);
    }

    @Override
    public void onStart(StreamClient client) {
        streamResponseListener.onStart(context);
    }

    @Override
    public void onMessage(StreamClient client, String response) {
        if (StringUtil.noText(response) || "[DONE]".equalsIgnoreCase(response.trim()) || finishedFlag.get()) {
            notifyLastMessageAndStop(response);
            return;
        }

        try {
            JSONObject jsonObject = JSON.parseObject(response);
            AiMessage delta = messageParser.parse(jsonObject, chatContext);

            fullMessage.merge(delta);
            delta.setFullContent(fullMessage.getContent());

            if (delta.getContent() != null) {
                AiMessageResponse resp = new AiMessageResponse(chatContext, response, delta);
                streamResponseListener.onMessage(context, resp);
            }
        } catch (Exception err) {
            streamResponseListener.onFailure(context, err);
            onStop(this.context.getClient());
        }
    }

    private void notifyLastMessage(String response) {
        if (finishedFlag.compareAndSet(false, true)) {
            fullMessage.setFullContent(fullMessage.getContent());
            fullMessage.setContent(null);
            fullMessage.setFinished(true);
            AiMessageResponse resp = new AiMessageResponse(chatContext, response, fullMessage);
            streamResponseListener.onMessage(context, resp);
        }
    }

    private void notifyLastMessageAndStop(String response) {
        try {
            notifyLastMessage(response);
        } finally {
            if (stoppedFlag.compareAndSet(false, true)) {
                context.setAiMessage(fullMessage);
                streamResponseListener.onStop(context);
            }
        }
    }

    @Override
    public void onStop(StreamClient client) {
        try {
            notifyLastMessage(null);
        } finally {
            if (stoppedFlag.compareAndSet(false, true)) {
                context.setAiMessage(fullMessage);
                streamResponseListener.onStop(context);
            }
        }
    }

    @Override
    public void onFailure(StreamClient client, Throwable throwable) {
        context.setThrowable(throwable);
        streamResponseListener.onFailure(context, throwable);
    }
}
