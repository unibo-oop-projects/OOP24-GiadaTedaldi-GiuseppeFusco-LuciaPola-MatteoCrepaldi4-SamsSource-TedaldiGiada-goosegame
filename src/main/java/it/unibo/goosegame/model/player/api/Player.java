package it.unibo.goosegame.model.player.api;

import java.awt.Color;
import java.util.List;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;

/**
 * Class that stores information about players.
 */
public interface Player {
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

    /**
     * Getter for the player's satchel.
     *
     * @return the player's satchel
     */
    CardSatchelController getSatchel();
}
