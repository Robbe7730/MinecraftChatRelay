package be.robbevanherck.chatplugin.mixin;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftChatService;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow
    ServerPlayerEntity player;

    /**
     * Intercept a chat message and send it to the MinecraftChatService
     * @param packet The packet containing the chat message
     * @param ci Callback info provided by mixin
     */
    //TODO: remove the * and figure out how this works
    @Inject(method = "onChatMessage", at = @At(value="INVOKE", target = "net.minecraft.server.PlayerManager.broadcastChatMessage*"))
    private void onMessageBroadcast(ChatMessageC2SPacket packet, CallbackInfo ci) {
        Optional<ChatService> maybeMcService = ChatServiceRepository.getChatServices().stream()
                .filter(service -> service.getClass() == MinecraftChatService.class)
                .findFirst();
        if (maybeMcService.isPresent()) {
            MinecraftChatService mcChatService = (MinecraftChatService) maybeMcService.get();
            mcChatService.onMessageReceived(new Message(player.getDisplayName().asString(), packet.getChatMessage(), mcChatService));
        }
    }
}