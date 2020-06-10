package be.robbevanherck.chatplugin.entities;

/**
 * Interface for all Players (anything that can receive messages)
 */
public interface Player {
    /**
     * Get the name of the player
     * @return The name of the player
     */
    String getDisplayName();

    /**
     * Get a unique ID from the player, consisting of the name of the chat service and a unique id.
     * This last part is not enforced, but recommended
     * @return The unique ID
     */
    String getID();

    // You'll also want a public static Player findOrCreate(...) that queries the PlayerRepository for a player with the given
    // arguments and creates and saves one if it doesn't exist
}
