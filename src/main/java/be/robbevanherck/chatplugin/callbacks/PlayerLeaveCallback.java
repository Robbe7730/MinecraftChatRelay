package be.robbevanherck.chatplugin.callbacks;

import be.robbevanherck.chatplugin.util.Player;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface PlayerLeaveCallback {
    Event<PlayerLeaveCallback> EVENT = EventFactory.createArrayBacked(PlayerLeaveCallback.class,
            (listeners) -> (message) -> {
                for (PlayerLeaveCallback callback : listeners) {
                    callback.onPlayerLeave(message);
                }
            }
    );

    void onPlayerLeave(Player player);
}
