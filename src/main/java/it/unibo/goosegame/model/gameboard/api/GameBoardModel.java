package it.unibo.goosegame.model.gameboard.api;

import it.unibo.goosegame.model.player.api.Player;

/**
 * Model for the Game board.
 */
public interface GameBoardModel {
    /**
     * Method used to get the name to assign at the game frame.
     *
     * @return string with the name for the frame
     */
    String getWindowTitle();

    /**
     * Method used to trigger the dice throw.
     */
    void throwDices();

    /**
     * Method used to move a player on the game board.
     *
     * @param player the player to move
     * @param steps the number of steps to move the player
     * @param isForward true if the player should move forward, false if backward
     */
    void move(Player player, int steps, boolean isForward);
}
