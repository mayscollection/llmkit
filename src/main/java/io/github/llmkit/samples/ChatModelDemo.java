package io.github.llmkit.samples;

import io.github.llmkit.api.ChatModel;
import io.github.llmkit.api.ChatOptions;
import io.github.llmkit.api.ChatResponse;
import io.github.llmkit.chat.openai.OpenAIChatConfig;
import io.github.llmkit.chat.qwen.QwenChatConfig;
import io.github.llmkit.prompt.Prompt;

/**
 * Demo showcasing LLMKit usage.
 *
 * <p>Before running, set your API key as an environment variable:</p>
 * <pre>
 * export OPENAI_API_KEY=your-api-key
 * # or
 * export QWEN_API_KEY=your-api-key
 * </pre>
 */
public class ChatModelDemo {

    public static void main(String[] args) {
        // Choose which demo to run
        String provider = System.getenv("LLM_PROVIDER");
        if ("qwen".equalsIgnoreCase(provider)) {
            runQwenDemo();
        } else {
            runOpenAIDemo();
        }
    }

    private static void runOpenAIDemo() {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Please set OPENAI_API_KEY environment variable");
            return;
        }

        // Create model using builder
        ChatModel chatModel = OpenAIChatConfig.builder()
                .apiKey(apiKey)
                .model("gpt-4o")
                .build()
                .toModel();

        System.out.println("=== OpenAI Demo ===\n");

        // Simple chat
        System.out.println("1. Simple chat:");
        String response = chatModel.chat("What is Java in one sentence?");
        System.out.println("Response: " + response);
        System.out.println();

        // Chat with options
        System.out.println("2. Chat with options:");
        ChatOptions options = ChatOptions.builder()
                .temperature(0.7f)
                .maxTokens(100)
                .build();
        response = chatModel.chat("What is Python in one sentence?", options);
        System.out.println("Response: " + response);
        System.out.println();

        // Multi-turn conversation
        System.out.println("3. Multi-turn conversation:");
        ChatResponse fullResponse = chatModel.call(
                Prompt.chat()
                        .system("You are a helpful coding assistant. Be concise.")
                        .user("What is recursion?")
                        .assistant("Recursion is when a function calls itself to solve a problem by breaking it into smaller sub-problems.")
                        .user("Give me a simple example in Java.")
                        .build()
        );
        System.out.println("Response: " + fullResponse.getContent());
        if (fullResponse.getUsage() != null) {
            System.out.println("Tokens used: " + fullResponse.getUsage().getTotalTokens());
        }
        System.out.println();

        // Streaming
        System.out.println("4. Streaming response:");
        System.out.print("Response: ");
        chatModel.stream("Count from 1 to 5 slowly.", delta -> {
            System.out.print(delta);
        });
        System.out.println("\n");

        System.out.println("Demo completed!");
    }

    private static void runQwenDemo() {
        String apiKey = System.getenv("QWEN_API_KEY");
        if (apiKey == null || apiKey.isEmpty()) {
            System.out.println("Please set QWEN_API_KEY environment variable");
            return;
        }

        // Create model using builder
        ChatModel chatModel = QwenChatConfig.builder()
                .apiKey(apiKey)
                .model("qwen-turbo")
                .build()
                .toModel();

        System.out.println("=== Qwen Demo ===\n");

        // Simple chat
        System.out.println("1. Simple chat:");
        String response = chatModel.chat("What is Java in one sentence?");
        System.out.println("Response: " + response);
        System.out.println();

        // Streaming
        System.out.println("2. Streaming response:");
        System.out.print("Response: ");
        chatModel.stream("Count from 1 to 5 slowly.", delta -> {
            System.out.print(delta);
        });
        System.out.println("\n");

        System.out.println("Demo completed!");
    }
}
