package be.robbevanherck.chatplugin.services.discord;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import be.robbevanherck.chatplugin.entities.OnlineStatusPlayer;
import be.robbevanherck.chatplugin.enums.OnlineStatus;
import be.robbevanherck.chatplugin.repositories.PlayerRepository;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Player implementation for Discord users
 */
public class DiscordPlayer implements MessageablePlayer, OnlineStatusPlayer {
    protected static final Logger LOGGER = LogManager.getLogger();
    private final User discordUser;
    /**
     * Represents a player that can be sent direct messages
     *
     * @param user The Discord user
     */
    private DiscordPlayer(User user) {
        discordUser = user;
    }

    /**
     * Find a player in the PlayerRepository or create and store one if it doesn't exist
     * @param user The Discord user to base the Player on
     * @return The player corresponding to this User
     */
    public static DiscordPlayer findOrCreate(User user) {
        DiscordPlayer player = (DiscordPlayer) PlayerRepository.getPlayer("discord-" + user.getId());
        if (player == null) {
            player = new DiscordPlayer(user);
            PlayerRepository.addPlayer(player);
        }

        return player;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        discordUser.openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage(message.toString()).queue());
    }

    @Override
    public String getDisplayName() {
        return discordUser.getName();
    }

    @Override
    public String getID() {
        return "discord-" + discordUser.getId();
    }

    @Override
    public void setOnlineStatus(OnlineStatus status) {
        if (DiscordRepository.getChannel() == null) {
            LOGGER.warn("Trying to set online status while not being connected");
            return;
        }
        Role onlineRole = DiscordRepository.getGuild().getRolesByName("Online", true).get(0);
        Role offlineRole = DiscordRepository.getGuild().getRolesByName("Offline", true).get(0);
        switch (status) {
            case ONLINE:
                if (onlineRole == null) {
                    LOGGER.error("No \"online\" role in the guild");
                    return;
                }
                DiscordRepository.getGuild().addRoleToMember(discordUser.getId(), onlineRole).queue();
                DiscordRepository.getGuild().removeRoleFromMember(discordUser.getId(), offlineRole).queue();
                break;
            case OFFLINE:
                if (offlineRole == null) {
                    LOGGER.error("No \"offline\" role in the guild");
                    return;
                }
                DiscordRepository.getGuild().addRoleToMember(discordUser.getId(), offlineRole).queue();
                DiscordRepository.getGuild().removeRoleFromMember(discordUser.getId(), onlineRole).queue();
                break;
            default:
                throw new UnsupportedOperationException("Unknown status: " + status.toString());
        }
    }
}
