# LLMKit

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](LICENSE)

A lightweight Java framework for building LLM-powered applications.

## Features

- **Simple API**: Easy-to-use `ChatModel` interface
- **Multi-provider Support**: OpenAI, Qwen (Alibaba), and any OpenAI-compatible API
- **Streaming**: Real-time streaming responses
- **Multi-turn Conversations**: Built-in support for conversation history
- **Immutable Design**: Thread-safe, immutable configuration and options
- **Retry Mechanism**: Configurable retry with exponential backoff
- **Type-safe Exceptions**: Clear exception hierarchy for error handling

## Quick Start

### Maven

```xml
<dependency>
    <groupId>io.github.llmkit</groupId>
    <artifactId>llmkit</artifactId>
    <version>0.2.0</version>
</dependency>
```

### Basic Usage

```java
import io.github.llmkit.api.ChatModel;
import io.github.llmkit.chat.openai.OpenAIChatConfig;

// Create a model
ChatModel chatModel = OpenAIChatConfig.builder()
    .apiKey("your-api-key")
    .model("gpt-4o")
    .buildModel();

// Simple chat
String response = chatModel.chat("What is Java?");
System.out.println(response);

// Streaming
chatModel.stream("Tell me a story", delta -> {
    System.out.print(delta);
});
```

### With Options

```java
import io.github.llmkit.api.ChatOptions;

ChatOptions options = ChatOptions.builder()
    .temperature(0.7f)
    .maxTokens(1000)
    .build();

String response = chatModel.chat("Explain recursion", options);
```

### Multi-turn Conversation

```java
import io.github.llmkit.prompt.Prompt;
import io.github.llmkit.api.ChatResponse;

// Build a conversation
ChatResponse response = chatModel.call(
    Prompt.chat()
        .system("You are a helpful coding assistant")
        .user("What is recursion?")
        .assistant("Recursion is when a function calls itself...")
        .user("Show me an example in Java")
        .build()
);

System.out.println(response.getContent());
System.out.println("Tokens used: " + response.getUsage().getTotalTokens());
```

### Qwen (Alibaba Cloud)

```java
import io.github.llmkit.chat.qwen.QwenChatConfig;

ChatModel chatModel = QwenChatConfig.builder()
    .apiKey("your-api-key")
    .model("qwen-turbo")
    .buildModel();

String response = chatModel.chat("What is Java?");
```

## Architecture

```
io.github.llmkit/
├── api/                    # Public API
│   ├── ChatModel           # Main interface
│   ├── ChatOptions         # Request options (immutable)
│   └── ChatResponse        # Response with metadata
├── chat/                   # Provider implementations
│   ├── openai/             # OpenAI implementation
│   └── qwen/               # Alibaba Qwen implementation
├── exception/              # Exception hierarchy
│   ├── LLMKitException     # Base exception
│   ├── ChatException       # Chat errors
│   ├── ProviderException   # Provider-specific errors
│   ├── NetworkException    # Network errors
│   └── ParseException      # Parsing errors
├── message/                # Message types
│   ├── Message             # Base class
│   ├── UserMessage
│   ├── SystemMessage
│   └── AiMessage
└── prompt/                 # Prompt building
    ├── Prompt              # Interface with factory methods
    ├── SimplePrompt        # Single-turn prompts
    ├── ChatPrompt          # Multi-turn conversations
    └── ChatPromptBuilder   # Fluent builder
```

## Error Handling

```java
import io.github.llmkit.exception.*;

try {
    String response = chatModel.chat("Hello");
} catch (ProviderException e) {
    if (e.isRateLimitError()) {
        // Handle rate limiting
    } else if (e.isAuthenticationError()) {
        // Handle auth error
    }
} catch (NetworkException e) {
    // Handle network issues
} catch (ChatException e) {
    // Handle other chat errors
}
```

## Requirements

- Java 8+
- OkHttp 4.x
- Fastjson2

## License

Apache License 2.0 - see [LICENSE](LICENSE) for details.

## Changelog

### 0.2.0

- Migrated package to `io.github.llmkit`
- Added comprehensive exception hierarchy
- Redesigned `ChatOptions` as immutable with builder
- Added `ChatPrompt` for multi-turn conversations
- Added `HttpClientFactory` for connection reuse
- Added `ChatResponse` with usage statistics
- Improved API with Javadoc documentation
