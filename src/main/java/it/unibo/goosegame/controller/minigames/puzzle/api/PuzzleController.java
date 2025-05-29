package it.unibo.goosegame.controller.minigames.puzzle.api;

import java.util.Map;

import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Interface representing the controller of a Puzzle minigame.
 */
public interface PuzzleController {
    /**
     * Initilizes the controller by linking it to the view and starts the game.
     * This method must be called after constructing the controller and the view.
     */
    void startGame();

    /**
     * Attempts to move the tile at the given position. 
     * 
     * @param pos the position on the puzzle grid that was clicked
     */
    void clickHandler(Position pos);

    /**
     * Randomly shuffles the puzzle tiles to start a new game or reset the current one.
     */
    void shufflePuzzle();

    /** 
     * @return ad unmodifiable map representing the puzzle grid 
     */
    Map<Position, Integer> getGridData();

    /**
     * @return the state of the match.
     */
    GameState getGameState();

}
