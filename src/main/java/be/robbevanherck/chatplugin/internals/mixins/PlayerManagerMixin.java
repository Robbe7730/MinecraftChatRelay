package be.robbevanherck.chatplugin.internals.mixins;

import be.robbevanherck.chatplugin.entities.SystemMessage;
import be.robbevanherck.chatplugin.internals.DisplayServicePlayerUtil;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftPlayer;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.MessageCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerJoinCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerLeaveCallback;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.UUID;

/**
 * Mixin for the PlayerManager class
 */
@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    @Shadow @Final private MinecraftServer server;

    /**
     * Intercept a player join event and notify the callback
     * @param connection (unused)
     * @param player The player that joined
     * @param ci Callback information
     */
    @Inject(method="onPlayerConnect", at=@At(value="RETURN"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerJoinCallback.EVENT.invoker().onPlayerJoin(MinecraftPlayer.findOrCreate(player));
    }
    /**
     * Intercept a player leave event and notify the callback
     * @param player The player that left
     * @param ci Callback information
     */
    @Inject(method="remove", at=@At(value="HEAD"))
    private void onPlayerLeave(ServerPlayerEntity player, CallbackInfo ci) {
        PlayerLeaveCallback.EVENT.invoker().onPlayerLeave(MinecraftPlayer.findOrCreate(player));
    }

    @Inject(method="getPlayerList", at=@At(value="HEAD"))
    private void getPlayerList(CallbackInfoReturnable<List<ServerPlayerEntity>> cir) {
        for (ChatService service : ChatServiceRepository.getEnabledChatServices()) {
            if (service.getOnlineStatusPlayer() != null) {
                DisplayServicePlayerUtil.getInstance().addToPlayerList(service);
            }
        }
    }

    @Inject(method = "broadcastChatMessage", at=@At(value = "HEAD"))
    private void broadcastChatMessage(Text message, MessageType type, UUID sender, CallbackInfo ci) {
        if (!sender.equals(MinecraftChatService.getUUID())) {
            MessageCallback.EVENT.invoker().onMessage(new SystemMessage(
                    message.getString()
            ));
        }
    }
}
