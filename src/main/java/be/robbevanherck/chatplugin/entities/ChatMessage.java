package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.util.Player;

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
        return player.getDisplayName();
    }

    @Override
    public String toString() {
        return "<" + getUsername() + "> " + content;
    }
}
