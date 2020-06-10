package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.entities.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Repository for mappings between Players
 */
public class PlayerMappingRepository {
    protected static final Logger LOGGER = LogManager.getLogger();
    /**
     * Private constructor to hide the implicit public one
     */
    private PlayerMappingRepository() {}

    private static final Set<Set<Player>> playerEqualitySets = new HashSet<>();

    /**
     * Returns the players linked to the given player
     * @param player The player to find the links for
     * @return The set of players that are linked to this player (including the player himself)
     */
    public static Set<Player> getLinksFor(Player player) {
        List<Set<Player>> playerSets = playerEqualitySets.stream().filter(players -> players.contains(player)).collect(Collectors.toList());
        if (playerSets.size() > 1) {
            LOGGER.error("More than 1 equality set found for player {}", player.getID());
        }
        return playerSets.get(0);
    }

    /**
     * Link 2 players
     * @param player1 The first player
     * @param player2 The second player
     */
    public static void linkPlayers(Player player1, Player player2) {
        Set<Player> player1Set = getLinksFor(player1);
        Set<Player> player2Set = getLinksFor(player2);
        if (player1Set == null) {
            if (player2Set == null) {
                // Create a brand new set
                player1Set = new HashSet<>();
                player1Set.add(player1);
                player1Set.add(player2);
                playerEqualitySets.add(player1Set);
            } else {
                // Add player 1 to the set of player 2
                player2Set.add(player1);
            }
        } else {
            if (player2Set == null) {
                // Add player 2 to the set of player 1
                player1Set.add(player2);
            } else {
                // Remove player 1's linking and add its links to player 2's set
                player2Set.addAll(player1Set);
                playerEqualitySets.remove(player1Set);
            }
        }
    }
}
