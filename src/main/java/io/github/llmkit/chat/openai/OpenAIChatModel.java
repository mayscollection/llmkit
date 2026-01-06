package io.github.llmkit.chat.openai;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.llmkit.api.ChatModel;
import io.github.llmkit.api.ChatOptions;
import io.github.llmkit.api.ChatResponse;
import io.github.llmkit.core.http.HttpClientFactory;
import io.github.llmkit.core.http.LLMHttpClient;
import io.github.llmkit.core.http.StreamClient;
import io.github.llmkit.core.http.impl.SseStreamClient;
import io.github.llmkit.exception.ChatException;
import io.github.llmkit.exception.ParseException;
import io.github.llmkit.exception.ProviderException;
import io.github.llmkit.message.AiMessage;
import io.github.llmkit.message.Message;
import io.github.llmkit.prompt.Prompt;
import io.github.llmkit.util.Retryer;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * OpenAI-compatible chat model implementation.
 *
 * <p>This implementation works with OpenAI's API and any OpenAI-compatible
 * endpoints.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class OpenAIChatModel implements ChatModel {

    private final OpenAIChatConfig config;
    private final LLMHttpClient httpClient;

    /**
     * Creates a new OpenAIChatModel with the given configuration.
     *
     * @param config the configuration
     */
    public OpenAIChatModel(OpenAIChatConfig config) {
        this.config = config;
        this.httpClient = new LLMHttpClient(HttpClientFactory.getDefaultClient());
    }

    @Override
    public ChatResponse call(Prompt prompt, ChatOptions options) {
        if (options == null) {
            options = ChatOptions.DEFAULT;
        }

        String url = config.getFullUrl();
        Map<String, String> headers = buildHeaders();
        String body = buildRequestBody(prompt, options, false);

        int retryCount = options.getRetryEnabledOrDefault(config.isRetryEnabled())
                ? options.getRetryCountOrDefault(config.getRetryCount())
                : 0;
        int retryDelay = options.getRetryDelayMsOrDefault(config.getRetryInitialDelayMs());

        String response;
        if (retryCount > 0) {
            response = Retryer.retry(() -> httpClient.post(url, headers, body), retryCount, retryDelay);
        } else {
            response = httpClient.post(url, headers, body);
        }

        return parseResponse(response);
    }

    @Override
    public void stream(Prompt prompt, Consumer<String> onDelta, ChatOptions options) {
        if (options == null) {
            options = ChatOptions.DEFAULT;
        }
        if (onDelta == null) {
            throw new IllegalArgumentException("onDelta callback must not be null");
        }

        String url = config.getFullUrl();
        Map<String, String> headers = buildHeaders();
        String body = buildRequestBody(prompt, options, true);

        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<Throwable> errorRef = new AtomicReference<>();
        StringBuilder fullContent = new StringBuilder();

        SseStreamClient streamClient = new SseStreamClient();
        streamClient.start(url, headers, body, new StreamClient.StreamListener() {
            @Override
            public void onMessage(StreamClient client, String data) {
                if ("[DONE]".equals(data)) {
                    return;
                }
                try {
                    String delta = parseStreamDelta(data);
                    if (delta != null && !delta.isEmpty()) {
                        fullContent.append(delta);
                        onDelta.accept(delta);
                    }
                } catch (Exception e) {
                    // Log but continue processing
                }
            }

            @Override
            public void onError(StreamClient client, Throwable throwable) {
                errorRef.set(throwable);
            }

            @Override
            public void onClose(StreamClient client) {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ChatException("Stream interrupted", e);
        }

        Throwable error = errorRef.get();
        if (error != null) {
            if (error instanceof ChatException) {
                throw (ChatException) error;
            }
            throw new ChatException("Stream failed: " + error.getMessage(), error);
        }
    }

    private Map<String, String> buildHeaders() {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "Bearer " + config.getApiKey());
        return headers;
    }

    private String buildRequestBody(Prompt prompt, ChatOptions options, boolean stream) {
        JSONObject body = new JSONObject();

        // Model
        String model = options.getModelOrDefault(config.getModel());
        body.put("model", model);

        // Messages
        JSONArray messages = new JSONArray();
        for (Message msg : prompt.getMessages()) {
            JSONObject msgObj = new JSONObject();
            msgObj.put("role", msg.getRole());
            msgObj.put("content", msg.getContent());
            messages.add(msgObj);
        }
        body.put("messages", messages);

        // Options
        if (options.getTemperature() != null) {
            body.put("temperature", options.getTemperature());
        }
        if (options.getMaxTokens() != null) {
            body.put("max_tokens", options.getMaxTokens());
        }
        if (options.getTopP() != null) {
            body.put("top_p", options.getTopP());
        }
        if (options.getFrequencyPenalty() != null) {
            body.put("frequency_penalty", options.getFrequencyPenalty());
        }
        if (options.getPresencePenalty() != null) {
            body.put("presence_penalty", options.getPresencePenalty());
        }

        // Stream
        if (stream) {
            body.put("stream", true);
            if (Boolean.TRUE.equals(options.getIncludeUsage())) {
                JSONObject streamOptions = new JSONObject();
                streamOptions.put("include_usage", true);
                body.put("stream_options", streamOptions);
            }
        }

        // Extra parameters
        if (options.getExtra() != null && !options.getExtra().isEmpty()) {
            body.putAll(options.getExtra());
        }

        return body.toJSONString();
    }

    private ChatResponse parseResponse(String response) {
        if (response == null || response.isEmpty()) {
            throw new ParseException("Empty response from API");
        }

        try {
            JSONObject json = JSON.parseObject(response);

            // Check for error
            JSONObject error = json.getJSONObject("error");
            if (error != null && !error.isEmpty()) {
                String message = error.getString("message");
                String code = error.getString("code");
                String type = error.getString("type");
                throw new ProviderException(message, code, type, response);
            }

            // Parse choices
            JSONArray choices = json.getJSONArray("choices");
            if (choices == null || choices.isEmpty()) {
                throw new ParseException("No choices in response", response);
            }

            JSONObject choice = choices.getJSONObject(0);
            JSONObject messageObj = choice.getJSONObject("message");
            String content = messageObj != null ? messageObj.getString("content") : null;
            String finishReason = choice.getString("finish_reason");

            // Parse usage
            ChatResponse.Usage usage = null;
            JSONObject usageObj = json.getJSONObject("usage");
            if (usageObj != null) {
                usage = new ChatResponse.Usage(
                        usageObj.getIntValue("prompt_tokens", 0),
                        usageObj.getIntValue("completion_tokens", 0),
                        usageObj.getIntValue("total_tokens", 0)
                );
            }

            return ChatResponse.builder()
                    .message(content != null ? new AiMessage(content) : null)
                    .rawResponse(response)
                    .usage(usage)
                    .finishReason(finishReason)
                    .build();

        } catch (ChatException e) {
            throw e;
        } catch (Exception e) {
            throw ParseException.invalidJson(response, e);
        }
    }

    private String parseStreamDelta(String data) {
        try {
            JSONObject json = JSON.parseObject(data);
            JSONArray choices = json.getJSONArray("choices");
            if (choices != null && !choices.isEmpty()) {
                JSONObject choice = choices.getJSONObject(0);
                JSONObject delta = choice.getJSONObject("delta");
                if (delta != null) {
                    return delta.getString("content");
                }
            }
        } catch (Exception ignored) {
            // Ignore parsing errors for individual chunks
        }
        return null;
    }

    /**
     * Returns the configuration.
     *
     * @return the config
     */
    public OpenAIChatConfig getConfig() {
        return config;
    }
}
