package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;

public class PlayerMessage extends Message {
    String username;
    ChatService origin;

    public PlayerMessage(String username, String content, ChatService origin) {
        super(content,origin);
        this.username = username;
        this.origin = origin;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "<" + username + "> " + content;
    }
}
