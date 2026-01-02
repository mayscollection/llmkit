# llmkit

一个轻量的 Java AI 框架。

## 功能
- 同步对话：`chat("...")`
- 流式对话：`chatStream("...", delta -> { ... })`
- OpenAI 兼容客户端
- Qwen 兼容客户端（OpenAI 兼容模式）

## 快速开始

### OpenAI

```java
ChatModel chatModel = OpenAIChatConfig.builder()
    .endpoint("https://api.openai.com")
    .provider("openai")
    .model("gpt-3.5-turbo")
    .apiKey("YOUR_API_KEY")
    .buildModel();

String output = chatModel.chat("简单介绍一下你自己");
chatModel.chatStream("简单介绍一下你自己", delta -> System.out.print(delta));
```

### Qwen

```java
ChatModel chatModel = QwenChatConfig.builder()
        .endpoint("https://dashscope.aliyuncs.com")
        .provider("qwen")
        .model("qwen-turbo")
        .apiKey("YOUR_API_KEY")
        .buildModel();

String output = chatModel.chat("简单介绍一下你自己");
chatModel.chatStream("简单介绍一下你自己", delta -> System.out.print(delta));
```