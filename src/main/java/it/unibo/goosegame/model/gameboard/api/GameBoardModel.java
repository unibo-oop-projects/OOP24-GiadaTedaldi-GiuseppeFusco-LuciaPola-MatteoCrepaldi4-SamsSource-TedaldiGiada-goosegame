package it.unibo.goosegame.model.gameboard.api;

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
}
