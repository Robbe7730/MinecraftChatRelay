package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.services.minecraft.callbacks.ChatMessageCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerDeathCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerJoinCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerLeaveCallback;
import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.entities.SystemMessage;
import be.robbevanherck.chatplugin.services.ChatService;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.LiteralText;

/**
 * The ChatService implementation for Minecraft
 */
public class MinecraftChatService implements ChatService {
    MinecraftServer minecraftServer;

    @Override
    public void serverStarted(MinecraftServer server) {
        this.minecraftServer = server;
        ChatMessageCallback.EVENT.register((message -> this.onMessageReceived(message, this)));
        PlayerJoinCallback.EVENT.register((player -> this.onMessageReceived(new SystemMessage(player.getDisplayName() + " joined the game"), this)));
        PlayerLeaveCallback.EVENT.register((player -> this.onMessageReceived(new SystemMessage(player.getDisplayName() + " left the game"), this)));
        PlayerDeathCallback.EVENT.register((deathMessage -> this.onMessageReceived(new SystemMessage(deathMessage), this)));
    }

    @Override
    public void serverStopped(MinecraftServer server) {
        this.minecraftServer = null;
    }

    @Override
    public void sendMessage(Message message) {
        if (this.minecraftServer != null) {
            this.minecraftServer.getPlayerManager().sendToAll(new LiteralText(message.toString()));
        }
    }
}
