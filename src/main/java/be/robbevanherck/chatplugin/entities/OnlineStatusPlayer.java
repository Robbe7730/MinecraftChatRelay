package be.robbevanherck.chatplugin.entities;

import be.robbevanherck.chatplugin.enums.OnlineStatus;

/**
 * Represents a player that can have an online status
 */
public interface OnlineStatusPlayer extends Player {
    /**
     * Sets the online status of this player
     * @param status The new status
     */
    void setOnlineStatus(OnlineStatus status);
}
