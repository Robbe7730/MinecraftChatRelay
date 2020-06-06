package be.robbevanherck.chatplugin.entities;

public class SystemMessage extends Message {
    /**
     * A message coming from a non-player, like players leaving or joining
     * @param content The content of the message
     */
    public SystemMessage(String content) {
        super(content);
    }
}
