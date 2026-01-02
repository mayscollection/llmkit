package me.maydeng.llmkit.core.model.client;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

public class HttpClient {
    private static final MediaType JSON_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient okHttpClient;

    public HttpClient() {
        this(new OkHttpClient());
    }

    public HttpClient(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public String post(String url, Map<String, String> headers, String payload) {
        Request.Builder builder = new Request.Builder().url(url);
        if (headers != null && !headers.isEmpty()) {
            headers.forEach(builder::addHeader);
        }

        RequestBody body = RequestBody.create(payload == null ? "" : payload, JSON_TYPE);
        Request request = builder.post(body).build();

        try (Response response = okHttpClient.newCall(request).execute();
             ResponseBody responseBody = response.body()) {
            return responseBody != null ? responseBody.string() : null;
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
