package be.robbevanherck.chatplugin.entities;

public class Message {
    String message;
    String username;

    public Message(String username, String message) {
        this.message = message;
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public String getUsername() {
        return username;
    }
}
