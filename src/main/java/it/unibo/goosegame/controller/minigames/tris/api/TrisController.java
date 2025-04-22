package it.unibo.goosegame.controller.minigames.tris.api;

import it.unibo.goosegame.utilities.Position;

/*
 * Interface representing the controller of a Tris(Tic-Tac-Toe) minigame.
 */
public interface TrisController {

    /**
     * Handles a moves made by the human player at the specified position.
     * 
     * @param position the position on the board where the human player wants to move
     */
    void movesMaker(Position position);

    /**
     * Updates the view based on the current state of the game model.
     */
    void updateView();

    /**
     * Ends the current round by disabling user input and showing the round result.
     * If the match is over(best of 3), the final result is displayed.
     */
    void endGame();

}
