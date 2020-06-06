package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import net.dv8tion.jda.api.entities.User;

/**
 * Player implementation for Discord users
 */
public class DiscordPlayer implements MessageablePlayer {
    private final User discordUser;
    /**
     * Represents a player that can be sent direct messages
     *
     * @param user The Discord user
     */
    public DiscordPlayer(User user) {
        discordUser = user;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        discordUser.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(message.toString()).queue());
    }

    @Override
    public String getDisplayName() {
        return discordUser.getName();
    }
}
