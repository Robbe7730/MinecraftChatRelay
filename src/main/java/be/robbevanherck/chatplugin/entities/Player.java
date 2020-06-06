package be.robbevanherck.chatplugin.entities;

import net.minecraft.server.network.ServerPlayerEntity;

public class Player {
    private final String displayName;

    /**
     * Class for players
     * @param displayName The name of the player
     */
    public Player(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
