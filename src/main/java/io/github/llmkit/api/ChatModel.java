package io.github.llmkit.api;

import io.github.llmkit.exception.ChatException;
import io.github.llmkit.prompt.Prompt;
import io.github.llmkit.prompt.SimplePrompt;

import java.util.function.Consumer;

/**
 * The main interface for interacting with chat-based language models.
 *
 * <p>This interface provides both synchronous and streaming methods for
 * sending prompts to LLMs and receiving responses.</p>
 *
 * <h3>Synchronous Usage</h3>
 * <pre>{@code
 * ChatModel chatModel = OpenAIChatConfig.builder()
 *     .apiKey("your-api-key")
 *     .buildModel();
 *
 * // Simple string response
 * String response = chatModel.chat("What is Java?");
 *
 * // With options
 * String response = chatModel.chat("What is Java?",
 *     ChatOptions.builder().temperature(0.7f).build());
 *
 * // Full response with metadata
 * ChatResponse response = chatModel.call(Prompt.of("What is Java?"));
 * System.out.println(response.getContent());
 * System.out.println(response.getUsage());
 * }</pre>
 *
 * <h3>Streaming Usage</h3>
 * <pre>{@code
 * chatModel.stream("Tell me a story", delta -> {
 *     System.out.print(delta); // Print each token as it arrives
 * });
 * }</pre>
 *
 * @author LLMKit Contributors
 * @since 0.2.0
 */
public interface ChatModel {

    // ========== Synchronous Methods ==========

    /**
     * Sends a simple string prompt and returns the response content.
     *
     * @param prompt the prompt string
     * @return the response content
     * @throws ChatException if the request fails
     */
    default String chat(String prompt) {
        return chat(prompt, ChatOptions.DEFAULT);
    }

    /**
     * Sends a prompt with options and returns the response content.
     *
     * @param prompt  the prompt string
     * @param options the chat options
     * @return the response content
     * @throws ChatException if the request fails
     */
    default String chat(String prompt, ChatOptions options) {
        ChatResponse response = call(SimplePrompt.user(prompt), options);
        return response != null ? response.getContent() : null;
    }

    /**
     * Sends a Prompt object and returns the response content.
     *
     * @param prompt the prompt
     * @return the response content
     * @throws ChatException if the request fails
     */
    default String chat(Prompt prompt) {
        ChatResponse response = call(prompt, ChatOptions.DEFAULT);
        return response != null ? response.getContent() : null;
    }

    /**
     * Sends a Prompt with options and returns the response content.
     *
     * @param prompt  the prompt
     * @param options the chat options
     * @return the response content
     * @throws ChatException if the request fails
     */
    default String chat(Prompt prompt, ChatOptions options) {
        ChatResponse response = call(prompt, options);
        return response != null ? response.getContent() : null;
    }

    /**
     * Sends a prompt and returns the full response.
     *
     * @param prompt the prompt
     * @return the full chat response
     * @throws ChatException if the request fails
     */
    default ChatResponse call(Prompt prompt) {
        return call(prompt, ChatOptions.DEFAULT);
    }

    /**
     * Sends a prompt with options and returns the full response.
     *
     * <p>This is the primary method that implementations must provide.</p>
     *
     * @param prompt  the prompt
     * @param options the chat options
     * @return the full chat response
     * @throws ChatException if the request fails
     */
    ChatResponse call(Prompt prompt, ChatOptions options);

    // ========== Streaming Methods ==========

    /**
     * Streams a response for the given prompt.
     *
     * @param prompt  the prompt string
     * @param onDelta callback for each response delta
     * @throws ChatException if the request fails
     */
    default void stream(String prompt, Consumer<String> onDelta) {
        stream(prompt, onDelta, ChatOptions.DEFAULT);
    }

    /**
     * Streams a response with options.
     *
     * @param prompt  the prompt string
     * @param onDelta callback for each response delta
     * @param options the chat options
     * @throws ChatException if the request fails
     */
    default void stream(String prompt, Consumer<String> onDelta, ChatOptions options) {
        stream(SimplePrompt.user(prompt), onDelta, options);
    }

    /**
     * Streams a response for the given Prompt.
     *
     * @param prompt  the prompt
     * @param onDelta callback for each response delta
     * @throws ChatException if the request fails
     */
    default void stream(Prompt prompt, Consumer<String> onDelta) {
        stream(prompt, onDelta, ChatOptions.DEFAULT);
    }

    /**
     * Streams a response with full control.
     *
     * <p>This is the primary streaming method that implementations must provide.</p>
     *
     * @param prompt  the prompt
     * @param onDelta callback for each response delta
     * @param options the chat options
     * @throws ChatException if the request fails
     */
    void stream(Prompt prompt, Consumer<String> onDelta, ChatOptions options);
}
