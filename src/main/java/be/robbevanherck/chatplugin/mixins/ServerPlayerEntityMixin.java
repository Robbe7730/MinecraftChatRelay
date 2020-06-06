package be.robbevanherck.chatplugin.mixins;

import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerDeathCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends LivingEntity {
    // This is here, just to make sure it compiles...
    protected ServerPlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    /**
     * Intercept a player dying and notify the callback.
     * @param source (unused) The source that killed the player
     * @param ci Callback information
     */
    @Inject(method="onDeath", at=@At(value="INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;getScoreboardTeam()Lnet/minecraft/scoreboard/AbstractTeam;"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        boolean deathMessagesEnabled = super.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES);
        if (deathMessagesEnabled) {
            Text deathMessage = super.getDamageTracker().getDeathMessage();
            PlayerDeathCallback.EVENT.invoker().onPlayerDeath(deathMessage.asString());
        }
    }
}
