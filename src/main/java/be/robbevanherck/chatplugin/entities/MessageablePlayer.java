package be.robbevanherck.chatplugin.entities;

public abstract class MessageablePlayer extends Player {

    /**
     * Represents a player that can be sent direct messages
     * @param username the username of the player
     */
    public MessageablePlayer(String username) {
        super(username);
    }

    public abstract void sendMessage(ChatMessage message);
}
