package me.maydeng.llmkit.core.model.client;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.maydeng.llmkit.core.message.AiMessage;
import me.maydeng.llmkit.core.model.chat.BaseChatModel;
import me.maydeng.llmkit.core.model.chat.ChatContext;
import me.maydeng.llmkit.core.model.chat.ChatContextHolder;
import me.maydeng.llmkit.core.model.chat.StreamResponseListener;
import me.maydeng.llmkit.core.model.chat.response.AiMessageResponse;
import me.maydeng.llmkit.core.model.client.impl.SseClient;
import me.maydeng.llmkit.core.parser.AiMessageParser;
import me.maydeng.llmkit.core.parser.impl.DefaultAiMessageParser;
import me.maydeng.llmkit.core.util.Retryer;
import me.maydeng.llmkit.core.util.StringUtil;

public class OpenAIChatClient extends ChatClient {

    private HttpClient httpClient;
    private AiMessageParser<JSONObject> aiMessageParser;

    public OpenAIChatClient(BaseChatModel<?> chatModel) {
        super(chatModel);
    }

    public HttpClient getHttpClient() {
        if (httpClient == null) {
            httpClient = new HttpClient();
        }
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public StreamClient getStreamClient() {
        return new SseClient();
    }

    public AiMessageParser<JSONObject> getAiMessageParser() {
        if (aiMessageParser == null) {
            aiMessageParser = DefaultAiMessageParser.getOpenAIMessageParser();
        }
        return aiMessageParser;
    }

    public void setAiMessageParser(AiMessageParser<JSONObject> aiMessageParser) {
        this.aiMessageParser = aiMessageParser;
    }

    @Override
    public AiMessageResponse chat() {
        ChatContext context = ChatContextHolder.currentContext();
        ChatRequestSpec requestSpec = context.getRequestSpec();

        String response = requestSpec.getRetryCount() > 0
                ? Retryer.retry(() -> getHttpClient().post(requestSpec.getUrl(), requestSpec.getHeaders(), requestSpec.getBody()),
                requestSpec.getRetryCount(), requestSpec.getRetryInitialDelayMs())
                : getHttpClient().post(requestSpec.getUrl(), requestSpec.getHeaders(), requestSpec.getBody());

        if (StringUtil.noText(response)) {
            return AiMessageResponse.error(context, response, "empty response");
        }
        return parseResponse(response, context);
    }

    protected AiMessageResponse parseResponse(String response, ChatContext context) {
        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject error = jsonObject.getJSONObject("error");
        if (error != null && !error.isEmpty()) {
            String message = error.getString("message");
            return AiMessageResponse.error(context, response, message);
        }

        AiMessage aiMessage = getAiMessageParser().parse(jsonObject, context);
        return new AiMessageResponse(context, response, aiMessage);
    }

    @Override
    public void chatStream(StreamResponseListener listener) {
        StreamClient streamClient = getStreamClient();
        ChatContext context = ChatContextHolder.currentContext();

        StreamClientListener clientListener = new BaseStreamClientListener(
                chatModel,
                context,
                streamClient,
                listener,
                getAiMessageParser()
        );

        ChatRequestSpec requestSpec = context.getRequestSpec();
        if (requestSpec.getRetryCount() > 0) {
            Retryer.retry(() -> streamClient.start(requestSpec.getUrl(), requestSpec.getHeaders(), requestSpec.getBody(),
                            clientListener, chatModel.getConfig()),
                    requestSpec.getRetryCount(),
                    requestSpec.getRetryInitialDelayMs());
        } else {
            streamClient.start(requestSpec.getUrl(), requestSpec.getHeaders(), requestSpec.getBody(),
                    clientListener, chatModel.getConfig());
        }
    }
}
