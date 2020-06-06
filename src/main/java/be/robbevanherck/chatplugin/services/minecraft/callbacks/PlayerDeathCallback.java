package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface PlayerDeathCallback {
    Event<PlayerDeathCallback> EVENT = EventFactory.createArrayBacked(PlayerDeathCallback.class,
            (listeners) -> (deathMessage) -> {
                for (PlayerDeathCallback callback : listeners) {
                    callback.onPlayerDeath(deathMessage);
                }
            }
    );

    void onPlayerDeath(String deathMessage);
}
