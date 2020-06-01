package be.robbevanherck.chatplugin;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.services.minecraft.MinecraftChatService;
import net.fabricmc.api.ModInitializer;

import java.util.ArrayList;
import java.util.List;

public class ChatPlugin implements ModInitializer {
    List<ChatService> chatServices = new ArrayList<>();

    @Override
    public void onInitialize() {
        chatServices.forEach(ChatService::init);
    }
}