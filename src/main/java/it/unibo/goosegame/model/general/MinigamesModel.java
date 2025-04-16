package it.unibo.goosegame.model.general;

/**
 * Represents the general interface for a minigame model.
 * All minigames must implement this interface to ensure consistent behavior.
 */
public interface MinigamesModel {

    /**
     * Resets the game to its initial state.
     */
    void resetGame();

    /**
     * Returns the result of the game.
     * The meaning of the result value depends on the specific minigame implementation.
     * 
     * @return an integer representing the game result
     */
    int getResult();


    /**
     * @return the name of the minigame as a String
     */
    String getName();

    /**
     * Checks whether the game has ended.
     * 
     * @return true if the game is over, false otherwise
     */
    boolean isOver();

}
