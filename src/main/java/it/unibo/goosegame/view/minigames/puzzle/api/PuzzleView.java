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

    /**
     * Updates the view of the puzzle grid, reflecting the current state of the puzzle.
     */
    void updateView();

    /**
     * Updates the timer display label with the given time string.
     * 
     * @param time the time string to display 
     */
    void updateTimerLabel(String time);

    /**
     * Displays a message indicating game result.
     * 
     * @param result the result of the mini game
     */
    void showResultMessage(boolean result);

    /**
     * Ends the game showing a win message, by disabling the buttons and setting the default close operation.
     */
    void endGame();

}
