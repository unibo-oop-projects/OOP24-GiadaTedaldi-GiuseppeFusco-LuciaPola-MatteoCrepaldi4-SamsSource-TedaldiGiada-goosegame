package it.unibo.goosegame.utilities.player.api;

import java.awt.*;

public interface Player {
    public static final Color[] COLORS = { Color.red, Color.yellow, Color.green, Color.blue, Color.cyan };

    /**
     * Updates the player position
     *
     * @param steps number of steps the player has to take
     * @param isForward wether the steps are forward or backwards
     */
    void move(int steps, boolean isForward);

    /**
     * Moves the player to the specified cell index
     *
     * @param cellIndex index of the cell
     */
    void goTo(int cellIndex);

    int getPosition();
}
