package it.unibo.goosegame.view.minigames.puzzle.api;

/**
 * Read Only interface for interacting with the puzzle view without exposing any mutable state.
 */
public interface ReadPuzzleView {

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
