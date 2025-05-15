package it.unibo.goosegame.view.minigames.puzzle.api;

/**
 * Interface representing the view of a Puzzle minigame.
 */
public interface PuzzleView {

    /**
     * Updates the view of the puzzle grid, reflecting the current state of the puzzle.
     */
    void updateView();

    /**
     * Displays a message indicating that the puzzle has been completed successfully.
     */
    void showWinMessage();

    /**
     * Stops the game timer if it's currently running.
     */
    void stopTimer();
}
