package it.unibo.goosegame.model.player.api;

import java.awt.Color;

import it.unibo.goosegame.controller.cardsatchel.CardSatchelController;

/**
 * Class that stores information about players.
 */
public interface Player {
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
     * Method used to update the index of the cell the player is in.
     *
     * @param index the new index of the cell
     */
    void setIndex(int index);

    /**
     * Getter for the player's satchel.
     *
     * @return the player's satchel
     */
    CardSatchelController getSatchel();

    /**
     * Method used to set the player's satchel.
     *
     * @param satchel the new satchel for the player
     */
    void setSatchel(CardSatchelController satchel);
}
