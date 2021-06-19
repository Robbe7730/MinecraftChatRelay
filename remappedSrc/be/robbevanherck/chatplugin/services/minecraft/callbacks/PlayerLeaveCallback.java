package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import be.robbevanherck.chatplugin.entities.Player;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback handler for player leaving
 */
public interface PlayerLeaveCallback {
    Event<PlayerLeaveCallback> EVENT = EventFactory.createArrayBacked(PlayerLeaveCallback.class,
            (listeners) -> (message) -> {
                for (PlayerLeaveCallback callback : listeners) {
                    callback.onPlayerLeave(message);
                }
            }
    );

    /**
     * Called on a player leaving
     * @param player The player that left
     */
    void onPlayerLeave(Player player);
}
