package be.robbevanherck.chatplugin.services;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.entities.PlayerMessage;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import net.minecraft.server.MinecraftServer;

public abstract class ChatService {
    /**
     * Called when the server starts
     */
    public abstract void serverStarted(MinecraftServer server);

    /**
     * Called when the server stops
     */
    public abstract void serverStopped(MinecraftServer server);

    /**
     * Forward a received message
     * @param message The message to be forwarded
     */
    public void onMessageReceived(PlayerMessage message) {
        ChatServiceRepository.getChatServices().stream()
                .filter(service -> !message.getOrigin().equals(service))
                .forEach(service -> service.sendMessage(message));
    }

    /**
     * Send the message using the Service
     * @param message the message to be sent
     */
    public abstract void sendMessage(Message message);
}
