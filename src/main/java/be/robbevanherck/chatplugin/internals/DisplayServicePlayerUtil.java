package be.robbevanherck.chatplugin.internals;

import be.robbevanherck.chatplugin.internals.entities.ChatServiceServerPlayerEntity;
import be.robbevanherck.chatplugin.services.ChatService;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

/**
 * Utils for displaying the ChatService in the player list
 */
public class DisplayServicePlayerUtil {
    private final MinecraftServer server;
    private static DisplayServicePlayerUtil instance;

    private DisplayServicePlayerUtil(MinecraftServer server) {
        this.server = server;
    }

    /**
     * Create a new instance
     * @param server The connected server
     */
    public static void createInstance(MinecraftServer server) {
        instance = new DisplayServicePlayerUtil(server);
    }

    public static DisplayServicePlayerUtil getInstance() {
        return instance;
    }

    /**
     * Update the player list for a certain service
     * @param service The service to update
     */
    public void addToPlayerList(ChatService service) {
        if (service.getOnlineStatusPlayer() != null) {
            ServerPlayerEntity spe = new ChatServiceServerPlayerEntity(server, service);
            server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, spe));
        }
    }

    /**
     * Update the player list for a certain service
     * @param service The service to update
     */
    public void removePlayerFromList(ChatService service) {
        if (service.getOnlineStatusPlayer() != null) {
            ServerPlayerEntity spe = new ChatServiceServerPlayerEntity(server, service);
            server.getPlayerManager().sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, spe));
        }
    }

    /**
     * Return the short string (<=16 characters)
     * @param str The original string
     * @return The first 16 characters (at most) of this string
     */
    public String shortString(String str) {
        if (str.length() <= 16) {
            return str;
        }
        return str.substring(0, 16);
    }
}
