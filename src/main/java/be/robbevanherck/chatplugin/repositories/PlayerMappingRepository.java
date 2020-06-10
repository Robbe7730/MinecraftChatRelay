package be.robbevanherck.chatplugin.repositories;

import be.robbevanherck.chatplugin.entities.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
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

    private static final Map<String, PlayerMapping> namePlayerMappings = new HashMap<>();
    private static final Random random = new Random();

    /**
     * Returns the players linked to the given player
     * @param player The player to find the links for
     * @return The set of players that are linked to this player (including the player himself)
     */
    public static PlayerMapping getMappingFor(Player player) {
        List<PlayerMapping> playerMappings = namePlayerMappings.values().stream().filter(players -> players.contains(player)).collect(Collectors.toList());
        if (playerMappings.size() > 1) {
            LOGGER.error("More than 1 equality set found for player {}", player.getID());
        }
        if (playerMappings.isEmpty()) {
            return null;
        }
        return playerMappings.get(0);
    }

    /**
     * Add a new mapping for one player
     * @param player The player to make the mapping for
     * @return The newly created PlayerMapping
     */
    public static PlayerMapping addMappingForPlayer(Player player) {
        String name = player.getDisplayName() + "-" + random.nextInt(1000);
        PlayerMapping ret = new PlayerMapping(name);
        ret.add(player);
        namePlayerMappings.put(name, ret);
        return ret;
    }

    /**
     * Get a mapping from a name
     * @param name The name
     * @return The mapping with this name
     */
    public static PlayerMapping getMappingByName(String name) {
        List<PlayerMapping> playerMappings = namePlayerMappings.values().stream().filter(players -> players.getHumanReadableName().equals(name)).collect(Collectors.toList());
        if (playerMappings.size() > 1) {
            LOGGER.error("More than 1 equality set found for name {}", name);
        }
        if (playerMappings.isEmpty()) {
            return null;
        }
        return playerMappings.get(0);
    }

    /**
     * Add a player to a mapping found by the name of the mapping, calling chatCallback with the message to be sent to
     * the requesting player.
     * @param player The player to add
     * @param name The name to find the mapping by
     * @param chatCallback The callback to call with the message
     * @return true if the request succeeded, false otherwise
     */
    public static boolean addPlayerToMappingByName(Player player, String name, ChatCallback chatCallback) {
        PlayerMappingRepository.PlayerMapping mapping = PlayerMappingRepository.getMappingByName(name);
        if (mapping == null) {
            chatCallback.sendMessage("No such mapping: " + name, true);
            return false;
        } else {
            if (mapping.contains(player)) {
                chatCallback.sendMessage("You were already in " + name, false);
                return false;
            } else {
                for (Player otherPlayer : mapping) {
                    if (otherPlayer instanceof MessageablePlayer) {
                        ((MessageablePlayer) otherPlayer).sendMessage(new ChatMessage(
                                player,
                                "Hi! I'm now linked to you."
                        ));
                    }
                }
                mapping.add(player);
                chatCallback.sendMessage("You were added to " + name, false);
                return true;
            }
        }
    }

    /**
     * Create a new mapping for a player, not creating a new one when one already exists
     * @param player The player in question
     * @param chatCallback The callback to send a message
     */
    public static void createMappingForPlayerChecked(Player player, ChatCallback chatCallback) {
        PlayerMappingRepository.PlayerMapping playerMapping = PlayerMappingRepository.getMappingFor(player);

        if (playerMapping != null) {
            chatCallback.sendMessage("You were already linked to " + playerMapping.getHumanReadableName(), false);
        } else {
            playerMapping = PlayerMappingRepository.addMappingForPlayer(player);
            chatCallback.sendMessage("You are now linked to " + playerMapping.getHumanReadableName(), false);
        }
    }

    /**
     * A mapping between players
     */
    public static class PlayerMapping extends HashSet<Player> {
        private final String humanReadableName;

        /**
         * Create a mapping, given a name
         * @param humanReadableName The name to use for this mapping
         */
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

    /**
     * A callback to send a single message
     */
    @FunctionalInterface
    public interface ChatCallback {
        /**
         * Send a message
         * @param message The message
         * @param isError Indicates if the message is an error message
         */
        void sendMessage(String message, boolean isError);
    }
}
