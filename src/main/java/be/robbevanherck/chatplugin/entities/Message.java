package be.robbevanherck.chatplugin.entities;

public class Message {
    String content;
    String username;

    public Message(String username, String content) {
        this.content = content;
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }
}
