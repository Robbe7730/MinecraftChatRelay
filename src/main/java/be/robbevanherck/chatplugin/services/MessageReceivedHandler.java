package be.robbevanherck.chatplugin.services;

import be.robbevanherck.chatplugin.entities.Message;

public interface MessageReceivedHandler {
    /**
     * Called when a message is received
     * @param message the received message
     */
    void receivedMessage(Message message);
}
