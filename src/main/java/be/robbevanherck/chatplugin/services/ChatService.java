package be.robbevanherck.chatplugin.services;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import net.minecraft.server.MinecraftServer;

/**
 * Interface for all chat services
 */
public interface ChatService {
    /**
     * Called when the server starts
     * @param server The server that just started
     */
    void serverStarted(MinecraftServer server);

    /**
     * Called when the server stops
     * @param server The server that is about to stop
     */
    void serverStopped(MinecraftServer server);

    /**
     * Forward a received message
     * @param message The message to be forwarded
     * @param origin The origin of the message, so the message doesn't get sent back to where it came from
     */
    default void onMessageReceived(Message message, ChatService origin) {
        ChatServiceRepository.getChatServices()
                .stream()
                .filter(service -> !origin.equals(service))
                .forEach(service -> service.sendMessage(message));
    }

    /**
     * Send the message using the Service
     * @param message the message to be sent
     */
    void sendMessage(Message message);
}
