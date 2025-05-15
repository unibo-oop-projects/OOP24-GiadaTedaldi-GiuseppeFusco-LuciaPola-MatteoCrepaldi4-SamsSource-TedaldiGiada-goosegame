package it.unibo.goosegame.controller.minigames.puzzle.api;

import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.utilities.Position;

/**
 * Interface representing the controller of a Puzzle minigame.
 */
public interface PuzzleController {

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
     * @return the puzzle model associated with this controller
     */
    PuzzleModel getModel();
}
