package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;

public class ChatMessage extends Message {
    String username;
    ChatService origin;

    public ChatMessage(String username, String content) {
        super(content);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "<" + username + "> " + content;
    }
}
