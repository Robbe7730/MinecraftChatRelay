package be.robbevanherck.chatplugin.callbacks;

import be.robbevanherck.chatplugin.entities.Message;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface PlayerJoinCallback {
    Event<PlayerJoinCallback> EVENT = EventFactory.createArrayBacked(PlayerJoinCallback.class,
            (listeners) -> (message) -> {
                for (PlayerJoinCallback callback : listeners) {
                    callback.onPlayerJoin(message);
                }
            }
    );

    void onPlayerJoin(Message message);
}
