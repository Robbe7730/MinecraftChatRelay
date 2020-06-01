package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.services.ChatService;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.LiteralText;

public class MinecraftChatService extends ChatService {
    MinecraftServer minecraftServer;

    @Override
    public void serverStarted(MinecraftServer server) {
        this.minecraftServer = server;
    }

    @Override
    public void serverStopped(MinecraftServer server) {
        this.minecraftServer = null;
    }

    @Override
    public void sendMessage(Message message) {
        if (this.minecraftServer != null) {
            this.minecraftServer.getPlayerManager().sendToAll(new LiteralText(message.getContent()));
        }
    }
}
