package be.robbevanherck.chatplugin.services.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;

/**
 * Repository for keeping the connected Discord channel
 */
public class DiscordRepository {
    /**
     * Private constructor to hide the implicit public one
     */
    private DiscordRepository() {}

    private static MessageChannel channel;
    private static Guild guild;

    /**
     * Get the channel the bot is connected to
     * @return The channel
     */
    public static MessageChannel getChannel() {
        return channel;
    }

    /**
     * Set the currently connected channel and guild, if there was a channel before send them a goodbye message
     *  and send a hello message to the new channel
     * @param newChannel The new channel
     * @param newGuild The new guild
     */
    public static void setChannel(MessageChannel newChannel, Guild newGuild) {
        if (newChannel == null) {
            if (channel != null) {
                channel.sendMessage("I must go, see you on the other side!").queue();
            }
        } else {
            if (channel != null) {
                channel.sendMessage("I must go, " + newChannel.getName() + " needs me").queue();
            }
            channel = newChannel;
            channel.sendMessage("Hello there").queue();
        }

        guild = newGuild;
    }
    /**
     * Get the guild the bot is connected to
     * @return The guild
     */
    public static Guild getGuild() {
        return guild;
    }
}
