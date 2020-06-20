package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.enums.OnlineStatus;

/**
 * Represents a player that can have an online status. This is not necessarily the online status of this player itself.
 * It is used to represent the online status of another user.
 */
public interface OnlineStatusPlayer extends Player {
    /**
     * Sets the online status of this player
     * @param status The new status
     */
    void setOnlineStatus(OnlineStatus status);

    /**
     * Return the online status of this player
     * @return The online status
     */
    OnlineStatus getOnlineStatus();
}
