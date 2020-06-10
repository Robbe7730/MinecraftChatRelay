package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.entities.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository for Players
 */
public class PlayerRepository {
    /**
     * Private constructor to hide the implicit public one
     */
    private PlayerRepository() {}

    private static final Map<String, Player> players = new HashMap<>();

    /**
     * Get a player from an ID.
     * @param id The ID of the player
     * @return All players
     */
    public static Player getPlayer(String id) {
        return players.get(id);
    }

    /**
     * Add a player
     * @param player The player to be added
     */
    public static void addPlayer(Player player) {
        players.put(player.getID(), player);
    }
}
