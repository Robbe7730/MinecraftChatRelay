package be.robbevanherck.chatplugin;

import be.robbevanherck.chatplugin.commands.SendAllCommand;
import be.robbevanherck.chatplugin.repositories.ChatServiceRepository;
import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.services.discord.DiscordChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftChatService;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;

public class ChatPlugin implements ModInitializer {
    @Override
    public void onInitialize() {
        // Add the chat services
        ChatServiceRepository.addChatService(new MinecraftChatService());
        ChatServiceRepository.addChatService(new DiscordChatService());

        // Initialize them
        ChatServiceRepository.getChatServices().forEach(ChatService::init);

        // Register the commands
        CommandRegistrationCallback.EVENT.register(SendAllCommand::register);
    }
}