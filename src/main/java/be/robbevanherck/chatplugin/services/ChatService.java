package be.robbevanherck.chatplugin.services;

import be.robbevanherck.chatplugin.entities.Message;

import java.util.Set;

public abstract class ChatService {
    Set<MessageReceivedHandler> handlers;

    /**
     * Called on plugin init, useful for adding callbacks
     */
    public abstract void init();

    /**
     * Add a MessageReceivedHandler to be called when a message is received
     * @param handler The MessageReceivedHandler to be added
     */
    public void addMessageReceivedHandler(MessageReceivedHandler handler) {
        handlers.add(handler);
    }

    /**
     * Send the message using the Service
     * @param message the message to be sent
     */
    public abstract void sendMessage(Message message);
}
