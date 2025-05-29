package it.unibo.goosegame.controller.minigames.tris.api;

import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Interface representing the controller of a Tris(Tic-Tac-Toe) minigame.
 */
public interface TrisController {
    /**
     * Initilizes the controller by linking it to the view and starts the game.
     * This method must be called after constructing the controller and the view.
     */
    void startGame();

    /**
     * Handles a moves made by the human player at the specified position.
     * 
     * @param position the position on the board where the human player wants to move
     */
    void makeMove(Position position);

    /**
     * @return the state of the match.
     */
    GameState getGameState();
}
