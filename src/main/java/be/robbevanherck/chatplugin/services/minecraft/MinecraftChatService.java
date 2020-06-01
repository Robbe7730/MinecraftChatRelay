package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.services.ChatService;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;

public class MinecraftChatService extends ChatService {
    MinecraftServer minecraftServer;

    @Override
    public void serverStarted(MinecraftServer server) {
        this.minecraftServer = server;
    }

    @Override
    public void sendMessage(Message message) {
        this.minecraftServer.getPlayerManager().sendToAll(new LiteralText(message.getContent()));
    }
}
