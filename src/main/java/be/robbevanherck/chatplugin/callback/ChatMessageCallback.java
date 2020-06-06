package be.robbevanherck.chatplugin.callback;

import be.robbevanherck.chatplugin.entities.Message;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public interface ChatMessageCallback {
    Event<ChatMessageCallback> EVENT = EventFactory.createArrayBacked(ChatMessageCallback.class,
            (listeners) -> (message) -> {
                for (ChatMessageCallback callback : listeners) {
                    callback.onChatMessage(message);
                }
            }
    );

    void onChatMessage(Message message);
}
