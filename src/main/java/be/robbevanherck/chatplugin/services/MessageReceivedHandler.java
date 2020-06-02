package be.robbevanherck.chatplugin.services;

import be.robbevanherck.chatplugin.entities.PlayerMessage;

public interface MessageReceivedHandler {
    /**
     * Called when a message is received
     * @param message the received message
     */
    void receivedMessage(PlayerMessage message);
}
