package be.robbevanherck.chatplugin;

import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import be.robbevanherck.chatplugin.services.discord.DiscordChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftChatService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fabricmc.fabric.api.event.server.ServerStopCallback;

/**
 * The main entrypoint for the ChatPlugin
 */
public class ChatPlugin implements ModInitializer {
    @Override
    public void onInitialize() {
        // Add the chat services
        ChatServiceRepository.addChatService(new MinecraftChatService());
        ChatServiceRepository.addChatService(new DiscordChatService());

        // Set a listener for server start
        ServerStartCallback.EVENT.register(server -> ChatServiceRepository.getChatServices().forEach(service -> service.serverStarted(server)));

        // Set a listener for server stop
        ServerStopCallback.EVENT.register(server -> ChatServiceRepository.getChatServices().forEach(service -> service.serverStopped(server)));

        // Allow the services to register commands
        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicatedServer) -> ChatServiceRepository.getChatServices().forEach(service -> service.registerCommands(commandDispatcher, dedicatedServer)));
    }
}