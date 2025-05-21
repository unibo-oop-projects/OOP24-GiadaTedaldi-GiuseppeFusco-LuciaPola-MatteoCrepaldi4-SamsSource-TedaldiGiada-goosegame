package it.unibo.goosegame.model.minigames.puzzle.api;

import java.util.Map;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;

/**
 * interface representing the logic of a puzzle minigame.
 * Extends the generic {@link MinigamesModel} interface.
 */
public interface PuzzleModel extends MinigamesModel {

    /**
     * Handles a hit at the given position.
     * This tipically attempts to move the tile if the move in valid.
     * 
     * @param pos the position of the tile to interact with 
     * @return true if the click was successfull, false otherwise
     */
    boolean hit(Position pos);

    /**
     * Shuffles the puzzle grid to create a randomized starting configuration.
     */
    void shuffle();

    /**
     * Sets the state indicating whether the time for the puzzle has run out.
     * 
     * @param timeOver true if the time is over, false otherwise
     */
    void setTimeOver(boolean timeOver);

    /**
     * Returns the current state of the puzzle grid.
     * Each entry associates a grid position with an integer representing a tile value.
     * 
     * @return a map representing the puzzle grid
     */
    Map<Position, Integer> getGrid();

}
