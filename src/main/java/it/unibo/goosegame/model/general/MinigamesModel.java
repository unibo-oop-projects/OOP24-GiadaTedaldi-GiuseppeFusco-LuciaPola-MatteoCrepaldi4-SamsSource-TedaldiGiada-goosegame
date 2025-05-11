package it.unibo.goosegame.model.general;

/**
 * Base interface for minigame models.
 * Defines common methods for managing the game state.
 */
public interface MinigamesModel {

    /**
     * General states of the minigame.
     */
    public enum GameState {
        ONGOING,
        WON,
        LOST
    }

    /**
     * Resets the game state to the beginning.
     */
    void resetGame();

    /**
     * Indicates whether the game is over.
     * @return true if the game is finished, false otherwise
     */
    boolean isOver();
}
