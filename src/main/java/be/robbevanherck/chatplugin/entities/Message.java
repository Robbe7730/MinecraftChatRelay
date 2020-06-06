package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;

public abstract class Message {
    String content;

    public Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return content;
    }
}
