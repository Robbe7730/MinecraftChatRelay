package be.robbevanherck.chatplugin.internals.entities;

import be.robbevanherck.chatplugin.entities.OnlineStatusPlayer;
import be.robbevanherck.chatplugin.enums.OnlineStatus;
import be.robbevanherck.chatplugin.internals.DisplayServicePlayerUtil;
import be.robbevanherck.chatplugin.services.ChatService;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.text.LiteralText;
import net.minecraft.world.GameMode;

/**
 * A ServerPlayerEntity for a ChatService
 */
public class ChatServiceServerPlayerEntity extends ServerPlayerEntity {
    ChatService chatService;

    /**
     * Create a new ChatServiceServerPlayerEntity from a server and a service
     * @param server The server where the service needs to be shown
     * @param service The service to show
     */
    public ChatServiceServerPlayerEntity(MinecraftServer server, ChatService service) {
        super(
                server,
                server.getOverworld(),
                new GameProfile(service.getOnlineStatusPlayer().getUUID(), DisplayServicePlayerUtil.getInstance().shortString(service.getOnlineStatusPlayer().getDisplayName())),
                new ServerPlayerInteractionManager(server.getOverworld())
        );
        OnlineStatusPlayer onlineStatusPlayer = service.getOnlineStatusPlayer();
        // Give it the full name
        this.setCustomName(new LiteralText(onlineStatusPlayer.getDisplayName()));
        // Make it look grey when not online
        this.setGameMode(service.getOnlineStatusPlayer().getOnlineStatus() == OnlineStatus.ONLINE ? GameMode.CREATIVE : GameMode.SPECTATOR);
        this.chatService = service;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ChatServiceServerPlayerEntity that = (ChatServiceServerPlayerEntity) o;

        return chatService.equals(that.chatService);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + chatService.hashCode();
        return result;
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        this.interactionManager.setGameMode(gameMode);
    }
}
