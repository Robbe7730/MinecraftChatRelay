package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

/**
 * Player implementation for Minecraft players
 */
public class MinecraftPlayer implements MessageablePlayer {
    ServerPlayerEntity serverPlayerEntity;

    /**
     * Player implementation for Minecraft players
     * @param serverPlayerEntity The Minecraft-player to base all data on
     */
    public MinecraftPlayer(ServerPlayerEntity serverPlayerEntity) {
        this.serverPlayerEntity = serverPlayerEntity;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        this.serverPlayerEntity.sendMessage(
                (new TranslatableText("commands.message.display.incoming", new LiteralText(message.getUsername()), new LiteralText(message.getContent())))
                        .formatted(Formatting.GRAY, Formatting.ITALIC)
        );
    }

    @Override
    public String getDisplayName() {
        return serverPlayerEntity.getDisplayName().asString();
    }
}
