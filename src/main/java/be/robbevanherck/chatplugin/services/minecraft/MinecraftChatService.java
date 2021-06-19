package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.Message;
import be.robbevanherck.chatplugin.entities.OnlineStatusPlayer;
import be.robbevanherck.chatplugin.entities.Player;
import be.robbevanherck.chatplugin.enums.OnlineStatus;
import be.robbevanherck.chatplugin.services.ChatService;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.MessageCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerJoinCallback;
import be.robbevanherck.chatplugin.services.minecraft.callbacks.PlayerLeaveCallback;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.UUID;

/**
 * The ChatService implementation for Minecraft
 */
public class MinecraftChatService extends ChatService {
    MinecraftServer minecraftServer;

    @Override
    public void serverStarted(MinecraftServer server) {
        this.minecraftServer = server;
        MessageCallback.EVENT.register((message -> this.onMessageReceived(message, this)));
        PlayerJoinCallback.EVENT.register(player -> {
            if (player instanceof OnlineStatusPlayer) {
                ((OnlineStatusPlayer) player).setOnlineStatus(OnlineStatus.ONLINE);
            }
        });
        PlayerLeaveCallback.EVENT.register(player -> {
            if (player instanceof OnlineStatusPlayer) {
                ((OnlineStatusPlayer) player).setOnlineStatus(OnlineStatus.OFFLINE);
            }
        });
        this.enable();
    }

    @Override
    public void serverStopped(MinecraftServer server) {
        this.minecraftServer = null;
    }

    @Override
    public void sendMessage(Message message) {
        if (this.minecraftServer != null) {
            this.minecraftServer.getPlayerManager().broadcastChatMessage(new LiteralText(message.toString()), MessageType.CHAT, getUUID());
        }
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> commandDispatcher, boolean dedicatedServer) {
        MinecraftVerifyCommand.register(commandDispatcher);
    }

    @Override
    public OnlineStatusPlayer getOnlineStatusPlayer() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Minecraft";
    }

    public static UUID getUUID() {
        return UUID.fromString("44774637-78bb-4c66-a847-69b1a978c93c");
    }
}
