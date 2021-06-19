package be.robbevanherck.chatplugin.services.minecraft;

import be.robbevanherck.chatplugin.entities.ChatMessage;
import be.robbevanherck.chatplugin.entities.MessageablePlayer;
import be.robbevanherck.chatplugin.repositories.PlayerRepository;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.UUID;

/**
 * Player implementation for Minecraft players
 */
public class MinecraftPlayer implements MessageablePlayer {
    ServerPlayerEntity serverPlayerEntity;

    /**
     * Player implementation for Minecraft players
     * @param serverPlayerEntity The Minecraft-player to base all data on
     */
    private MinecraftPlayer(ServerPlayerEntity serverPlayerEntity) {
        this.serverPlayerEntity = serverPlayerEntity;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        this.serverPlayerEntity.sendMessage(
                (new TranslatableText("commands.message.display.incoming", new LiteralText(message.getUsername()), new LiteralText(message.getContent())))
                        .formatted(Formatting.GRAY, Formatting.ITALIC),
                false
        );
    }

    @Override
    public String getDisplayName() {
        return serverPlayerEntity.getDisplayName().asString();
    }

    @Override
    public String getID() {
        return "minecraft-" + serverPlayerEntity.getUuidAsString();
    }

    @Override
    public UUID getUUID() {
        return this.serverPlayerEntity.getUuid();
    }

    /**
     * Find a player in the PlayerRepository or create and store one if it doesn't exist
     * @param serverPlayerEntity The Minecraft-player user to base the Player on
     * @return The player
     */
    public static MinecraftPlayer findOrCreate(ServerPlayerEntity serverPlayerEntity) {
        MinecraftPlayer player = findByUUID(serverPlayerEntity.getUuid());

        if (player == null) {
            player = new MinecraftPlayer(serverPlayerEntity);
            PlayerRepository.addPlayer(player);
        }

        return player;
    }

    /**
     * Find a player in the PlayerRepository by UUID
     * @param uuid The UUID to find the player on
     * @return The player or null if no such player is found
     */
    public static MinecraftPlayer findByUUID(UUID uuid) {
        return (MinecraftPlayer) PlayerRepository.getPlayer("minecraft-" + uuid.toString());
    }
}
