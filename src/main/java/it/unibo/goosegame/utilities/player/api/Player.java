package it.unibo.goosegame.utilities.player.api;

import java.awt.Color;
import java.util.List;

/**
 * Class that stores information about players.
 */
public interface Player {
    /**
     * Updates the player position.
     *
     * @param steps number of steps the player has to take
     * @param isForward wether the steps are forward or backwards
     */
    void move(int steps, boolean isForward);

    /**
     * Moves the player to the specified cell index.
     *
     * @param cellIndex index of the cell
     */
    void goTo(int cellIndex);

    /**
     * Getter method for the player's name.
     *
     * @return the player's name
     */
    String getName();

    /**
     * Getter method for the player's position.
     *
     * @return index of the cell the player is in
     */
    int getPosition();

    /**
     * Getter method for the player's icon color.
     *
     * @return a {@link Color} object with the player's icon color
     */
    Color getColor();

    /**
     * Getter for the list of colors that can be assigned to players.
     *
     * @return list of colors
     */
    List<Color> getColorsList();
}
