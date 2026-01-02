package me.maydeng.llmkit.core.model.chat;

import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;
import me.maydeng.llmkit.core.model.client.StreamContext;
import org.slf4j.Logger;

public interface StreamResponseListener {
    Logger logger = org.slf4j.LoggerFactory.getLogger(StreamResponseListener.class);

    default void onStart(StreamContext context) {
    }

    void onMessage(StreamContext context, AiMessageResponse response);

    default void onStop(StreamContext context) {
    }

    default void onFailure(StreamContext context, Throwable throwable) {
        if (throwable != null) {
            logger.error(throwable.toString(), throwable);
        }
    }
}
