package it.unibo.goosegame.view.gameboard.api;

import it.unibo.goosegame.model.player.api.Player;

/**
 * Class storing all the view information for {@link it.unibo.goosegame.controller.gameboard.api.GameBoard}.
 */
public interface GameBoardView {

    /**
     * Used to make the frame of the application visible.
     */
    void show();

    /**
     * Used to draw the player on the table.
     *
     * @param player the object representing the player
     */
    void drawPlayer(Player player);

    /**
     * Used to update the information label on top of the frame.
     *
     * @param string the string to show on the label
     */
    void updateInformation(String string);
}
