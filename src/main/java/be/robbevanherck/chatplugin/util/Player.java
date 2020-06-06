package be.robbevanherck.chatplugin.util;

import net.minecraft.server.network.ServerPlayerEntity;

public class Player {
    private final String displayName;

    /**
     * Create a player from a {@link ServerPlayerEntity}
     * @param serverPlayerEntity The {@link ServerPlayerEntity} to base this player on
     */
    public Player(ServerPlayerEntity serverPlayerEntity) {
        this.displayName = serverPlayerEntity.getDisplayName().asString();
    }

    /**
     * Create a player with manually set parameters
     * @param displayName The name of the player
     */
    public Player(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
