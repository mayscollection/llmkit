package me.maydeng.llmkit.samples;

import me.maydeng.llmkit.chat.qwen.QwenChatConfig;
import me.maydeng.llmkit.core.model.chat.ChatModel;

public class ChatModelDemo {

    public static void main(String[] args) throws InterruptedException {
        ChatModel qwenChatModel = QwenChatConfig.builder()
                .model("qwen-plus")
                .apiKey("xxx")
                .buildModel();

        // 同步
        String output = qwenChatModel.chat("简单介绍一下你自己");
        System.out.println("sync> " + output);

        // 流式
        qwenChatModel.chatStream("简单介绍一下你自己", delta -> System.out.print(delta));

        Thread.sleep(20000L);
    }
}
