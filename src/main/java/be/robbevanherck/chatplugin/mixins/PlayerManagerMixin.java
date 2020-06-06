package be.robbevanherck.chatplugin.mixins;

import be.robbevanherck.chatplugin.callbacks.PlayerJoinCallback;
import be.robbevanherck.chatplugin.callbacks.PlayerLeaveCallback;
import be.robbevanherck.chatplugin.util.Player;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerManager.class)
public class PlayerManagerMixin {
    /**
     * Intercept a player join event and notify the callback
     * @param connection (unused)
     * @param player The player that joined
     * @param ci Callback information
     */
    @Inject(method="onPlayerConnect", at=@At(value="RETURN"))
    private void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerJoinCallback.EVENT.invoker().onPlayerJoin(new Player(player));
    }
    /**
     * Intercept a player leave event and notify the callback
     * @param player The player that left
     * @param ci Callback information
     */
    @Inject(method="remove", at=@At(value="RETURN"))
    private void onPlayerConnect(ServerPlayerEntity player, CallbackInfo ci) {
        PlayerLeaveCallback.EVENT.invoker().onPlayerLeave(new Player(player));
    }
}
