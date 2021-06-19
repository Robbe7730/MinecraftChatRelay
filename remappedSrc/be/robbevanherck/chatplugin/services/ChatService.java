package be.robbevanherck.chatplugin.services;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.entities.OnlineStatusPlayer;
import be.robbevanherck.chatplugin.internals.DisplayServicePlayerUtil;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;

/**
* Superclass for all chat services
 */
public abstract class ChatService {
    /**
     * Whether or not this chat service is enabled. This is not related to the online status of the OnlineStatusPlayer
     * and should not be used as such. For example, the Discord chat service uses this to indicate whether or not is is
     * active, but uses the online status of the Player to indicate if it is connected to a channel.
     *
     * WARNING: all chat services need to enable themselves (e.g. when the server starts), otherwise messages will not
     *          be forwarded.
     */
    private boolean enabled = false;

    /**
     * Called when the server starts
     * @param server The server that just started
     */
    public abstract void serverStarted(MinecraftServer server);

    /**
     * Called when the server stops
     * @param server The server that is about to stop
     */
    public abstract void serverStopped(MinecraftServer server);

    /**
     * Forward a received message
     * @param message The message to be forwarded
     * @param origin The origin of the message, so the message doesn't get sent back to where it came from
     */
    public void onMessageReceived(Message message, ChatService origin) {
        if (this.isEnabled()) {
            ChatServiceRepository.getEnabledChatServices()
                    .stream()
                    .filter(service -> !origin.equals(service))
                    .forEach(service -> service.sendMessage(message));
        }
    }

    /**
     * Send the message using the Service
     * @param message the message to be sent
     */
    public abstract void sendMessage(Message message);

    /**
     * Register the commands
     * @param commandDispatcher The CommandDispatcher required to register commands
     * @param dedicatedServer Is true if the server is a dedicated server
     */
    public abstract void registerCommands(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean dedicatedServer);

    /**
     * Return the Player to be shown in the player list
     * @return The OnlineStatusPlayer or null if it doesn't need to be shown
     */
    public abstract OnlineStatusPlayer getOnlineStatusPlayer();

    /**
     * Return the display name of this ChatService (does not have to equal the Players' display name)
     * @return The ChatService display name
     */
    public abstract String getDisplayName();

    /**
     * Disable the ChatService temporarily
     */
    public void disable() {
        this.enabled = false;

        // Update the tab-list
        DisplayServicePlayerUtil.getInstance().removePlayerFromList(this);

    }

    /**
     * Re-enable the ChatService
     */
    public void enable() {
        this.enabled = true;

        // Update the tab-list
        DisplayServicePlayerUtil.getInstance().addToPlayerList(this);
    }

    /**
     * Return if the ChatService is enabled
     * @return Whether or not the service is enabled
     */
    public boolean isEnabled() {
        return this.enabled;
    }
}
