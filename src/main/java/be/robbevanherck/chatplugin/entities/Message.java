package be.robbevanherck.chatplugin.entities;

public abstract class Message {
    String content;

    /**
     * An abstract class for all messages
     * @param content the content of the message
     */
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
