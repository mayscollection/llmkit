package io.github.llmkit.core.http;

import io.github.llmkit.exception.NetworkException;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * Synchronous HTTP client for LLM API calls.
 *
 * <p>This client handles HTTP POST requests to LLM APIs, including
 * proper error handling and response parsing.</p>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public class LLMHttpClient {

    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient okHttpClient;

    /**
     * Creates an LLMHttpClient using the shared default OkHttpClient.
     */
    public LLMHttpClient() {
        this(HttpClientFactory.getDefaultClient());
    }

    /**
     * Creates an LLMHttpClient with a custom OkHttpClient.
     *
     * @param okHttpClient the OkHttpClient to use
     * @throws IllegalArgumentException if okHttpClient is null
     */
    public LLMHttpClient(OkHttpClient okHttpClient) {
        if (okHttpClient == null) {
            throw new IllegalArgumentException("OkHttpClient must not be null");
        }
        this.okHttpClient = okHttpClient;
    }

    /**
     * Sends a POST request with JSON body.
     *
     * @param url     the request URL
     * @param headers the request headers
     * @param payload the JSON payload
     * @return the response body as a string
     * @throws NetworkException if the request fails
     */
    public String post(String url, Map<String, String> headers, String payload) {
        Request.Builder builder = new Request.Builder().url(url);

        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }

        RequestBody body = RequestBody.create(payload == null ? "" : payload, JSON_TYPE);
        Request request = builder.post(body).build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            ResponseBody responseBody = response.body();
            String responseString = responseBody != null ? responseBody.string() : null;

            if (!response.isSuccessful()) {
                throw new NetworkException(
                        "HTTP request failed with status " + response.code() + ": " + response.message(),
                        response.code(),
                        responseString
                );
            }

            return responseString;
        } catch (IOException e) {
            throw new NetworkException("Failed to execute HTTP request to " + url, e);
        }
    }

    /**
     * Returns the underlying OkHttpClient.
     *
     * @return the OkHttpClient instance
     */
    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
