package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import be.robbevanherck.chatplugin.entities.Message;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback handler for Minecraft chat messages
 */
public interface MessageCallback {
    // On other platforms, Event and EventFactoy will not be available, but these are pretty trivial to implement
    Event<MessageCallback> EVENT = EventFactory.createArrayBacked(MessageCallback.class,
            (listeners) -> (message) -> {
                for (MessageCallback callback : listeners) {
                    callback.onMessage(message);
                }
            }
    );

    /**
     * Called when a Minecraft-message is sent
     * @param message The message that was sent
     */
    void onMessage(Message message);
}
