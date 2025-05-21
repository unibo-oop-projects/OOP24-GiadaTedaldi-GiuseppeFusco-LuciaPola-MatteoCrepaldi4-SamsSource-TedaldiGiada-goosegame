package it.unibo.goosegame.controller.minigames.puzzle.api;

import java.util.Map;

import it.unibo.goosegame.utilities.Position;
import it.unibo.goosegame.view.minigames.puzzle.api.PuzzleView;

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
     * @return ad unmodifiable map representing the puzzle grid 
     */
    public Map<Position, Integer> getGridData();

    /**
     * Sets the view associated with this controller.
     * 
     * @param view the PuzzleView to be linked to the controller
     */
    public void setView(final PuzzleView view);
}
