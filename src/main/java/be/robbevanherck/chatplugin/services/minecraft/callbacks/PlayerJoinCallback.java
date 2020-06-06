package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import be.robbevanherck.chatplugin.entities.Player;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback handler for player joining
 */
public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class,
            (listeners) -> (message) -> {
                for (PlayerJoinCallback callback : listeners) {
                    callback.onPlayerJoin(message);
                }
            }
    );

    /**
     * Called on a player join
     * @param player The player that joined
     */
    void onPlayerJoin(Player player);
}
