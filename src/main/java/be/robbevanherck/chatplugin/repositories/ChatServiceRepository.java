package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.services.ChatService;

import java.util.ArrayList;
import java.util.List;

public class ChatServiceRepository {
    private static List<ChatService> chatServices;

    /**
     * Get all chat services. Initializes chatServices when it is null
     * @return All ChatServices
     */
    public static List<ChatService> getChatServices() {
        if (chatServices == null) {
            chatServices = new ArrayList<>();
        }
        return chatServices;
    }

    /**
     * Add a chat service
     * @param service The service to be added
     */
    public static void addChatService(ChatService service) {
        getChatServices().add(service);
    }

    /**
     * Remove a chat service
     * @param service The service to be removed
     */
    public static void removeChatService(ChatService service) {
        getChatServices().remove(service);
    }
}
