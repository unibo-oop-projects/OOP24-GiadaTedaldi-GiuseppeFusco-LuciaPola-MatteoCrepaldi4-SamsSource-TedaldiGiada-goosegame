package it.unibo.goosegame.model.minigames.honkmand.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Colors;
import java.util.List;

/**
 * API interface for the HonkMand minigame model.
 */
public interface HonkMandModel extends MinigamesModel {

    /**
     * Possible results after a player input.
     */
    enum InputResult {
    /** The input was correct, but the sequence is not yet complete. */
    CORRECT,
    /** The input completed the sequence, advancing to the next round. */
    NEXT_ROUND,
    /** The input was incorrect, resulting in game over. */
    GAME_OVER,
    /** The input completed the final sequence, resulting in a win. */
    GAME_WIN
}

    /**
     * Starts a new game, resetting score and sequences.
     */
    void startGame();

    /**
     * Advances to the next round, increasing the level and generating a new sequence.
     */
    void nextRound();

    /**
     * Checks the player's input against the sequence.
     * @param colorId the color chosen by the player
     * @return the result of the input
     */
    InputResult checkPlayerInput(Colors colorId);

    /**
     * Returns the sequence to be reproduced.
     * @return an unmodifiable copy of the sequence to be reproduced
     */
    List<Colors> getSequence();

    /**
     * Returns the current level.
     * @return the current level
     */
    int getLevel();

    /**
     * Returns the current score.
     * @return the current score
     */
    int getScore();

    /**
     * Returns the current game state.
     * @return the current game state
     */
    @Override
    GameState getGameState();
}
