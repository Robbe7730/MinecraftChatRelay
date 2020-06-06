package be.robbevanherck.chatplugin.services.minecraft.callbacks;

import be.robbevanherck.chatplugin.entities.Message;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ChatMessageCallback {
    // On other platforms, Event and EventFactoy will not be available, but these are pretty trivial to implement
    Event<ChatMessageCallback> EVENT = EventFactory.createArrayBacked(ChatMessageCallback.class,
            (listeners) -> (message) -> {
                for (ChatMessageCallback callback : listeners) {
                    callback.onChatMessage(message);
                }
            }
    );

    void onChatMessage(Message message);
}
