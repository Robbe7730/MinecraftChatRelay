package be.robbevanherck.chatplugin.services.discord;

import net.dv8tion.jda.api.entities.MessageChannel;

public class DiscordRepository {
    private static MessageChannel channel;

    /**
     * Get the channel the bot is connected to
     * @return The channel
     */
    public static MessageChannel getChannel() {
        return channel;
    }

    /**
     * Set the currently connected channel, if there was a channel before send them a goodbye message
     *  and send a hello message to the new channel
     * @param newChannel The new channel
     */
    public static void setChannel(MessageChannel newChannel) {
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
    }
}
