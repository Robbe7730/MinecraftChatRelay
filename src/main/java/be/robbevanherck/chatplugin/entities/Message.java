package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;

public abstract class Message {
    String content;
    ChatService origin;

    public Message(String content, ChatService origin) {
        this.content = content;
        this.origin = origin;
    }

    public String getContent() {
        return content;
    }

    public ChatService getOrigin() {
        return origin;
    }

    public String toString() {
        return content;
    }
}
