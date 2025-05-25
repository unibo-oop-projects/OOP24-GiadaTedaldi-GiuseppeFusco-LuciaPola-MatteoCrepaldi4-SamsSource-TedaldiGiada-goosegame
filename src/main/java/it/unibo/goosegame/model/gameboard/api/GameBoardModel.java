package it.unibo.goosegame.model.gameboard.api;

import it.unibo.goosegame.model.player.api.Player;

import java.util.List;

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
     * Getter method for the number of players.
     *
     * @return the number of players
     */
    int getNumberOfPlayers();

    /**
     * Getter method for the player list.
     *
     * @return list of the players
     */
    List<Player> getPlayers();
}
