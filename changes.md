# 变更记录

## [0.2.0] - 2025.1.6

### 重大变更
- **包名迁移**: 从 `me.maydeng.llmkit` 迁移到 `io.github.llmkit`
- **API 重构**: `ChatModel` 接口方法从 `chat()`/`chatStream()` 改为 `chat()`/`stream()`/`call()`

### 新增
- **异常体系**: 完整的异常层次结构
  - `LLMKitException` - 基础异常
  - `ConfigurationException` - 配置错误
  - `ChatException` - 聊天异常
  - `ProviderException` - 提供商返回的错误（含 rate limit、auth error 检测）
  - `NetworkException` - 网络异常
  - `ParseException` - 解析异常

- **多轮对话支持**
  - `ChatPrompt` - 支持任意消息序列的多轮对话
  - `ChatPromptBuilder` - 流式构建对话历史
  - `Prompt.chat()` 工厂方法

- **不可变设计**
  - `ChatOptions` - 不可变请求选项，支持 Builder 和 `with*()` 方法
  - `ChatContext` - 不可变请求上下文
  - `ChatOptions.DEFAULT` 静态常量

- **响应增强**
  - `ChatResponse` - 封装响应内容、原始响应、usage 统计、finish_reason
  - `ChatResponse.Usage` - Token 使用统计

- **HTTP 客户端优化**
  - `HttpClientFactory` - OkHttpClient 单例复用
  - `LLMHttpClient` - 同步 HTTP 客户端
  - `SseStreamClient` - SSE 流式客户端

- **Builder 优化**
  - `AbstractChatConfigBuilder` - 抽象 Builder 基类，减少代码重复

- **消息类型增强**
  - `MessageType` 枚举
  - `Message.getType()` 和 `Message.getRole()` 方法
  - 静态工厂方法：`UserMessage.of()`, `SystemMessage.of()`, `AiMessage.of()`

- **工具类**
  - `StringUtil` - 字符串工具
  - `Retryer` - 重试工具（使用框架异常）

- **项目配置**
  - Apache 2.0 LICENSE
  - 测试依赖：JUnit 5, Mockito, MockWebServer, AssertJ
  - 单元测试框架

### 改进
- 所有公共 API 添加 Javadoc 文档
- `BaseModelConfig.toString()` 脱敏 apiKey
- 配置类自动标准化 endpoint（去尾斜杠）和 requestPath（加首斜杠）
- 版本号升级到 0.2.0

### 移除
- 旧包 `me.maydeng.llmkit` 已删除

---

## [0.1.0] - 初始版本

### 新增
- 基础聊天核心（prompt、options、context、response）
- OpenAI 兼容聊天客户端（同步 + 流式）
- Qwen 兼容聊天模型与配置
