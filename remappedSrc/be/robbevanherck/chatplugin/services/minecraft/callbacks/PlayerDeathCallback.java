package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback handler for player deaths
 */
public interface PlayerDeathCallback {
    Event<PlayerDeathCallback> EVENT = EventFactory.createArrayBacked(PlayerDeathCallback.class,
            (listeners) -> (deathMessage) -> {
                for (PlayerDeathCallback callback : listeners) {
                    callback.onPlayerDeath(deathMessage);
                }
            }
    );

    /**
     * Called on a players death
     * @param deathMessage The message that should appear in chat
     */
    void onPlayerDeath(String deathMessage);
}
