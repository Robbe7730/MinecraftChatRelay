package be.robbevanherck.chatplugin.internals.mixins;

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
    @Inject(method = "onGameMessage", at = @At(value="INVOKE", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;filterText(Ljava/lang/String;Ljava/util/function/Consumer;)V"))
    private void onGameMessage(ChatMessageC2SPacket packet, CallbackInfo ci) {
        Message message = new ChatMessage(MinecraftPlayer.findOrCreate(player), packet.getChatMessage());
        ChatMessageCallback.EVENT.invoker().onChatMessage(message);
    }
}