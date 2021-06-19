package be.robbevanherck.chatplugin;

import be.robbevanherck.chatplugin.internals.DisplayServicePlayerUtil;
import be.robbevanherck.chatplugin.internals.commands.DisableCommand;
import be.robbevanherck.chatplugin.internals.commands.EnableCommand;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import be.robbevanherck.chatplugin.services.discord.DiscordChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftChatService;
import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.ServerCommandSource;

/**
 * The main entrypoint for the ChatPlugin
 */
public class ChatPlugin implements ModInitializer {
    @Override
    public void onInitialize() {
        // Add the chat services
        ChatServiceRepository.addChatService(new MinecraftChatService());
        ChatServiceRepository.addChatService(new DiscordChatService());

        // Set a listener to setup DisplayServicePlayerUtil
        ServerLifecycleEvents.SERVER_STARTED.register(DisplayServicePlayerUtil::createInstance);

        // Set a listener for server start
        ServerLifecycleEvents.SERVER_STARTED.register(server -> ChatServiceRepository.getChatServices().forEach(service -> service.serverStarted(server)));

        // Set a listener for server stop
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> ChatServiceRepository.getChatServices().forEach(service -> service.serverStopped(server)));

        // Register internal commands
        CommandRegistrationCallback.EVENT.register(ChatPlugin::registerCommands);

        // Allow the services to register commands
        CommandRegistrationCallback.EVENT.register((commandDispatcher, dedicatedServer) -> ChatServiceRepository.getChatServices().forEach(service -> service.registerCommands(commandDispatcher, dedicatedServer)));
    }

    private static void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicatedServer) {
        DisableCommand.register(dispatcher);
        EnableCommand.register(dispatcher);
    }
}