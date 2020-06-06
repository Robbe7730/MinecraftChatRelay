package be.robbevanherck.chatplugin.mixins;

import be.robbevanherck.chatplugin.services.minecraft.MinecraftPlayer;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.ChatMessageCallback;
import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.Message;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin for the ServerPlayNetworkHandler class
 */
@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
    /**
     * The current player
     */
    @Shadow
    public ServerPlayerEntity player;

    /**
     * Intercept a chat message and notify the callback
     * @param packet The packet containing the chat message
     * @param ci Callback info provided by mixin
     */
    @Inject(method = "onChatMessage", at = @At(value="INVOKE", target = "net.minecraft.server.PlayerManager.broadcastChatMessage(Lnet/minecraft/text/Text;Z)V"))
    private void onChatMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        Message message = new ChatMessage(new MinecraftPlayer(player), packet.getChatMessage());
        ChatMessageCallback.EVENT.invoker().onChatMessage(message);
    }
}