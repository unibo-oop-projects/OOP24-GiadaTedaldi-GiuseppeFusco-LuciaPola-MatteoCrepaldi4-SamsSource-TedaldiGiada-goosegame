package it.unibo.goosegame.view.minigames.puzzle.api;

import it.unibo.goosegame.controller.minigames.puzzle.api.PuzzleController;

/**
 * Interface representing the view of a Puzzle minigame.
 */
public interface PuzzleView {

    /**
     * Sets the controller.
     * 
     * @param controller the {@link PuzzleController} instance to be set for this view
     */
    void setController(PuzzleController controller);

}
