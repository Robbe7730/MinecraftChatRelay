package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class MinecraftPlayer extends MessageablePlayer {
    ServerPlayerEntity serverPlayerEntity;

    public MinecraftPlayer(ServerPlayerEntity serverPlayerEntity) {
        super(serverPlayerEntity.getDisplayName().asString());
        this.serverPlayerEntity = serverPlayerEntity;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        this.serverPlayerEntity.sendMessage(
                (new TranslatableText("commands.message.display.incoming", new LiteralText(message.getUsername()), new LiteralText(message.getContent())))
                        .formatted(Formatting.GRAY, Formatting.ITALIC)
        );
    }
}