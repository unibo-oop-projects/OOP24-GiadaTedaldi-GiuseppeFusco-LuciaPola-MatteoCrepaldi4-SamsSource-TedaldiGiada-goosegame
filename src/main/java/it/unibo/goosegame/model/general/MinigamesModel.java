package it.unibo.goosegame.model.general;

/**
 * Base interface for minigame models.
 * Defines common methods for managing the game state.
 */
public interface MinigamesModel {

    /**
     * General states of the minigame.
     */
    enum GameState {
        /**
         * The game is not started yet.
         */
        NOT_STARTED,
        /**
         * The game is ongoing.
         */
        ONGOING,
        /**
         * The game has been won.
         */
        WON,
        /**
         * The game has been lost.
         */
        LOST,
        /**
         * The game is tied.
         */
        TIE
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

    /**
     * Indicates the mini game name.
     * @return the mini game name.
     */
    String getName();

    /**
     * Indicates the mini game state.
     * @return the mini game state.
     */
    GameState getGameState();
}
