package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;

/**
 * Representing a message that comes from a player
 */
public class ChatMessage extends Message {
    Player player;
    ChatService origin;

    /**
     * A message from a player
     * @param player The player sending the message
     * @param content The contents of the message
     */
    public ChatMessage(Player player, String content) {
        super(content);
        this.player = player;
    }

    public String getUsername() {
        if (player != null) {
            return player.getDisplayName();
        }

        return "server";
    }

    @Override
    public String toString() {
        return "<" + getUsername() + "> " + content;
    }
}
