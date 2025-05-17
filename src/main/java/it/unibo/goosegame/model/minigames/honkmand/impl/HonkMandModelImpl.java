package it.unibo.goosegame.model.minigames.honkmand.impl;

import it.unibo.goosegame.model.minigames.honkmand.api.HonkMandModel;
import it.unibo.goosegame.utilities.Colors;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model class for the HonkMand minigame (a Simon-like memory game).
 * <p>
 * This class manages the game logic, including the sequence generation, player input checking,
 * level progression, score tracking, and game state transitions (playing, win, lose).
 * It implements the {@link HonkMandModel} interface for integration with the general minigame framework.
 */
public final class HonkMandModelImpl implements HonkMandModel {

    /**
     * Maximum level to win the game (now in HonkMandConstants).
     */
    public static final int MAX_LEVEL = it.unibo.goosegame.utilities.HonkMandConstants.MAX_LEVEL; // for backward compatibility

    private final List<Colors> sequence;
    private final List<Colors> playerSequence;
    private int level;
    private int score;
    private final Random random;
    private GameState gameState;

    /**
     * Constructs a HonkMandModel object and initializes the game state.
     */
    public HonkMandModelImpl() {
        sequence = new ArrayList<>();
        playerSequence = new ArrayList<>();
        level = 0;
        score = 0;
        random = new Random();
        gameState = GameState.NOT_STARTED;
    }

    /**
     * Starts a new game, resetting score and sequences.
     */
    @Override
    public void startGame() {
        sequence.clear();
        playerSequence.clear();
        level = 0;
        score = 0;
        gameState = GameState.ONGOING;
        nextRound();
    }

    /**
     * Advances to the next round, increasing the level and generating a new sequence.
     */
    @Override
    public void nextRound() {
        level++;
        playerSequence.clear();

        if (level > MAX_LEVEL) {
            gameState = GameState.WON;
            return;
        }
        generateNewSequence();
    }

    /**
     * Generates a new random sequence for the current level.
     */
    private void generateNewSequence() {
        sequence.clear();
        for (int i = 0; i < level; i++) {
            final int index = random.nextInt(Colors.values().length);
            sequence.add(Colors.values()[index]);
        }
    }

    /**
     * Checks the player's input against the sequence.
     * @param colorId the color chosen by the player
     * @return the result of the input
     */
    @Override
    public HonkMandModel.InputResult checkPlayerInput(final Colors colorId) {
        if (gameState != GameState.ONGOING) {
            return InputResult.GAME_OVER;
        }
        playerSequence.add(colorId);
        final int currentIndex = playerSequence.size() - 1;

        // Fix: check bounds before accessing sequence
        if (currentIndex >= sequence.size()) {
            gameState = GameState.LOST;
            return InputResult.GAME_OVER;
        }
        if (playerSequence.get(currentIndex).equals(sequence.get(currentIndex))) {
            if (playerSequence.size() == sequence.size()) {
                score++;
                if (level == MAX_LEVEL) {
                    gameState = GameState.WON;
                    return InputResult.GAME_WIN;
                }
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            gameState = GameState.LOST;
            return InputResult.GAME_OVER;
        }
    }

    /**
     * Returns a defensive copy of the sequence to avoid exposing internal representation.
     * @return an unmodifiable copy of the sequence to be reproduced
     */
    @Override
    public List<Colors> getSequence() {
        return List.copyOf(sequence); // Defensive copy to avoid exposing internal state
    }

    /**
     * Returns the current level.
     * @return the current level
     */
    @Override
    public int getLevel() {
        return level;
    }

    /**
     * Returns the current score.
     * @return the current score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Returns the current game state.
     * @return the current game state
     */
    @Override
    public GameState getGameState() {
        return gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
        startGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return gameState == GameState.LOST || gameState == GameState.WON;
    }
}
