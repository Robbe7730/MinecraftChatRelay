package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;

public class Message {
    String content;
    String username;
    ChatService origin;

    public Message(String username, String content, ChatService origin) {
        this.content = content;
        this.username = username;
        this.origin = origin;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public ChatService getOrigin() {
        return origin;
    }

    public String toString() {
        return "<" + username + "> " + content;
    }
}
