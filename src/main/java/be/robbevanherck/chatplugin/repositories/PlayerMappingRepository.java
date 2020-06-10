package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.entities.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
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

    private static final Set<PlayerMapping> playerEqualitySets = new HashSet<>();
    private static final Random random = new Random();

    /**
     * Returns the players linked to the given player
     * @param player The player to find the links for
     * @return The set of players that are linked to this player (including the player himself)
     */
    public static PlayerMapping getMappingFor(Player player) {
        List<PlayerMapping> playerMappings = playerEqualitySets.stream().filter(players -> players.contains(player)).collect(Collectors.toList());
        if (playerMappings.size() > 1) {
            LOGGER.error("More than 1 equality set found for player {}", player.getID());
        }
        return playerMappings.get(0);
    }

    /**
     * Link 2 players
     * @param player1 The first player
     * @param player2 The second player
     */
    public static void linkPlayers(Player player1, Player player2) {
        PlayerMapping player1Mapping = getMappingFor(player1);
        PlayerMapping player2Mapping = getMappingFor(player2);
        if (player1Mapping == null) {
            if (player2Mapping == null) {
                // Create a brand new set
                player1Mapping = new PlayerMapping(player1.getDisplayName() + " mapping " + random.nextInt(1000));
                player1Mapping.add(player1);
                player1Mapping.add(player2);
                playerEqualitySets.add(player2Mapping);
            } else {
                // Add player 1 to the set of player 2
                player2Mapping.add(player1);
            }
        } else {
            if (player2Mapping == null) {
                // Add player 2 to the set of player 1
                player1Mapping.add(player2);
            } else {
                // Remove player 1's linking and add its links to player 2's set
                player2Mapping.addAll(player1Mapping);
                playerEqualitySets.remove(player1Mapping);
            }
        }
    }

    private static class PlayerMapping extends HashSet<Player> {
        private final String humanReadableName;

        public PlayerMapping(String humanReadableName) {
            super();
            this.humanReadableName = humanReadableName;
        }

        public String getHumanReadableName() {
            return humanReadableName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;

            PlayerMapping players = (PlayerMapping) o;

            return humanReadableName.equals(players.humanReadableName);
        }

        @Override
        public int hashCode() {
            int result = super.hashCode();
            result = 31 * result + humanReadableName.hashCode();
            return result;
        }
    }
}
