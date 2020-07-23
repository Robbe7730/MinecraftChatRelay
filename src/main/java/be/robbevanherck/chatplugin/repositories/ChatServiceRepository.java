package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.services.ChatService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Repository for all ChatService-instances
 */
public class ChatServiceRepository {
    /**
     * Private constructor to hide the implicit public one
     */
    private ChatServiceRepository() {}

    private static Map<String, ChatService> chatServices;

    /**
     * Utility function to make sure that chatServices is never null
     * @return The mapping between display name and chat service
     */
    private static Map<String, ChatService> getChatServicesMap() {
        if (chatServices == null) {
            chatServices = new HashMap<>();
        }

        return chatServices;
    }

    /**
     * Get all chat services. Initializes chatServices when it is null
     * @return All ChatServices
     */
    public static Collection<ChatService> getChatServices() {
        return getChatServicesMap().values();
    }

    /**
     * Get all enabled chat services
     * @return All enabled chat services
     */
    public static List<ChatService> getEnabledChatServices() {
        return getChatServices().stream().filter(ChatService::isEnabled).collect(Collectors.toList());
    }

    /**
     * Get all disabled chat services
     * @return All disabled chat services
     */
    public static List<ChatService> getDisabledChatServices() {
        return getChatServices().stream().filter(service -> !service.isEnabled()).collect(Collectors.toList());
    }

    /**
     * Add a chat service
     * @param service The service to be added
     */
    public static void addChatService(ChatService service) {
        getChatServicesMap().put(service.getDisplayName(), service);
    }

    /**
     * Remove a chat service
     * @param service The service to be removed
     */
    public static void removeChatService(ChatService service) {
        getChatServicesMap().remove(service.getDisplayName());
    }

    /**
     * Get a chat service by its display name
     * @param name The display name of the service
     * @return The chat service or null
     */
    public static ChatService getChatServiceByName(String name) {
        return getChatServicesMap().get(name);
    }
}
