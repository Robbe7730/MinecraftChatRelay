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
}
