package be.robbevanherck.chatplugin.entities;

/**
 * Abstract superclass for all messages
 */
public abstract class Message {
    String content;

    /**
     * An abstract class for all messages
     * @param content the content of the message
     */
    protected Message(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String toString() {
        return content;
    }
}
