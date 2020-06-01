package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.services.ChatService;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;

public class MinecraftChatService extends ChatService {
    MinecraftServer minecraftServer;

    @Override
    public void init() {
        ServerStartCallback.EVENT.register(mcServer -> this.minecraftServer = mcServer);
    }

    @Override
    public void sendMessage(Message message) {
        if (this.minecraftServer != null) {
            this.minecraftServer.getPlayerManager().sendToAll(new LiteralText(message.getMessage()));
        }
    }
}
