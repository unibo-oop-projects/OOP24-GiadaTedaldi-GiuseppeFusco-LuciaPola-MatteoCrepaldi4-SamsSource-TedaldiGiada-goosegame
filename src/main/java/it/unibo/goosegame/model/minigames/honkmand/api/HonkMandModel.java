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
        CORRECT,
        NEXT_ROUND,
        GAME_OVER,
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
    GameState getGameState();
}
