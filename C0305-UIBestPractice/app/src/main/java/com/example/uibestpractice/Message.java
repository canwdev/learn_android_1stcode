package com.example.uibestpractice;

public class Message
{
    // 表示此为收到的信息
    public static final int TYPE_RECEIVED = 0;
    // 表示此为发送的信息
    public static final int TYPE_SENT = 1;
    // 消息内容
    private String content;
    // 消息类型
    private int type;

    public Message(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public int getType() {
        return type;
    }
}
