package be.robbevanherck.chatplugin.entities;

/**
 * Represents a player that can receive direct messages
 */
public interface MessageablePlayer extends Player {
    /**
     * Send a direct message to this player
     * @param message The message to send
     */
    void sendMessage(ChatMessage message);
}
