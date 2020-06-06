package be.robbevanherck.chatplugin.mixins;

import be.robbevanherck.chatplugin.callbacks.ChatMessageCallback;
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

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    public ServerPlayerEntity player;

    /**
     * Intercept a chat message and send it to the MinecraftChatService
     * @param packet The packet containing the chat message
     * @param ci Callback info provided by mixin
     */
    @Inject(method = "onChatMessage", at = @At(value="INVOKE", target = "net.minecraft.server.PlayerManager.broadcastChatMessage(Lnet/minecraft/text/Text;Z)V"))
    private void onMessageBroadcast(ChatMessageC2SPacket packet, CallbackInfo ci) {
        Message message = new ChatMessage(player.getDisplayName().asString(), packet.getChatMessage());
        ChatMessageCallback.EVENT.invoker().onChatMessage(message);
    }
}