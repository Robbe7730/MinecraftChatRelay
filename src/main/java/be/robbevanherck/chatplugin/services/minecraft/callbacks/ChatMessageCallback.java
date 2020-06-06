package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import be.robbevanherck.chatplugin.entities.Message;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * Callback handler for Minecraft chat messages
 */
public interface ChatMessageCallback {
    // On other platforms, Event and EventFactoy will not be available, but these are pretty trivial to implement
    Event<ChatMessageCallback> EVENT = EventFactory.createArrayBacked(ChatMessageCallback.class,
            (listeners) -> (message) -> {
                for (ChatMessageCallback callback : listeners) {
                    callback.onChatMessage(message);
                }
            }
    );

    /**
     * Called when a Minecraft-message is sent
     * @param message The message that was sent
     */
    void onChatMessage(Message message);
}
