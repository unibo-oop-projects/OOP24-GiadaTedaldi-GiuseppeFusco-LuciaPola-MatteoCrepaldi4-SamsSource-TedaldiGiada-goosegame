package it.unibo.goosegame.view.minigames.tris.api;

import it.unibo.goosegame.controller.minigames.tris.api.TrisController;
import it.unibo.goosegame.utilities.Position;

/**
 * Interface representing the view of a Tris(Tic-Tac-Toe) minigame.
 */
public interface TrisView {
    /**
     * Sets the controller.
     * 
     * @param controller the {@link TrisController} instance to be set for this view
     */
    void setController(TrisController controller);

    /**
     * Updates the button at the specified position with the given symbol.
     * 
     * @param pos the position on the board to update
     * @param symbol the symbol to display("X" or "O")
     */
    void updateButton(Position pos, String symbol);

    /**
     * Sets the current status message to be displayed to the user.
     * 
     * @param msg the message to display
     */
    void setStatus(String msg);

    /**
     * Disables all the buttons on the board.
     * It called when the game ends.
     */
    void disableButtons();

    /**
     * Shows the final result message and closes the.
     * 
     * @param result the final result message to display
     */
    void endGame(String result);

    /**
     * Resets the board each round.
     */
    void resetGrid();

}
