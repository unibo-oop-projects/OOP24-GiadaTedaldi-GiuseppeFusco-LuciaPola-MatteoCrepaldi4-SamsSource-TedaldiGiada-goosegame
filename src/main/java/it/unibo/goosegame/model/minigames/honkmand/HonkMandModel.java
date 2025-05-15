package it.unibo.goosegame.model.minigames.honkmand;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Colors;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model class for the HonkMand minigame (a Simon-like memory game).
 * <p>
 * This class manages the game logic, including the sequence generation, player input checking,
 * level progression, score tracking, and game state transitions (playing, win, lose).
 * It implements the {@link MinigamesModel} interface for integration with the general minigame framework.
 */
public final class HonkMandModel implements MinigamesModel {

    /**
     * Maximum level to win the game (now in HonkMandConstants).
     */
    public static final int MAX_LEVEL = it.unibo.goosegame.utilities.HonkMandConstants.MAX_LEVEL; // for backward compatibility

    private final List<Colors> sequence;
    private final List<Colors> playerSequence;
    private int level;
    private int score;
    private final Random random;

    /**
     * Possible states of the game.
     */
    public enum GameState {
        /** The game has not started yet. */
        NOT_STARTED,
        /** The game is in progress. */
        PLAYING,
        /** The game is over (lost). */
        GAME_OVER,
        /** The game is won. */
        GAME_WIN
    }

    private GameState gameState;

    /**
     * Possible results after a player input.
     */
    public enum InputResult {
        /** Input is correct, wait for next input. */
        CORRECT,
        /** Sequence completed, go to next round. */
        NEXT_ROUND,
        /** Wrong input, game lost. */
        GAME_OVER,
        /** Game won. */
        GAME_WIN
    }

    /**
     * Constructs a HonkMandModel object and initializes the game state.
     */
    public HonkMandModel() {
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
    public void startGame() {
        sequence.clear();
        playerSequence.clear();
        level = 0;
        score = 0;
        gameState = GameState.PLAYING;
        nextRound();
    }

    /**
     * Advances to the next round, increasing the level and generating a new sequence.
     */
    public void nextRound() {
        level++;
        playerSequence.clear();

        if (level > MAX_LEVEL) {
            gameState = GameState.GAME_WIN;
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
    public InputResult checkPlayerInput(final Colors colorId) {
        if (gameState != GameState.PLAYING) {
            return InputResult.GAME_OVER;
        }
        playerSequence.add(colorId);
        final int currentIndex = playerSequence.size() - 1;

        // Fix: check bounds before accessing sequence
        if (currentIndex >= sequence.size()) {
            gameState = GameState.GAME_OVER;
            return InputResult.GAME_OVER;
        }
        if (playerSequence.get(currentIndex).equals(sequence.get(currentIndex))) {
            if (playerSequence.size() == sequence.size()) {
                score++;
                if (level == MAX_LEVEL) {
                    gameState = GameState.GAME_WIN;
                    return InputResult.GAME_WIN;
                }
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            gameState = GameState.GAME_OVER;
            return InputResult.GAME_OVER;
        }
    }

    /**
     * Returns the sequence to be reproduced.
     * @return the sequence to be reproduced
     */
    public List<Colors> getSequence() {
        return sequence;
    }

    /**
     * Returns the current level.
     * @return the current level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Returns the current score.
     * @return the current score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current game state.
     * @return the current game state
     */
    public GameState getGameState() {
        return gameState;
    }

    // --- MinigamesModel implementation ---

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
        return gameState == GameState.GAME_OVER || gameState == GameState.GAME_WIN;
    }

    /**
     * Returns the general state of the minigame according to the MinigamesModel interface.
     * @return the general state of the minigame
     */
    public MinigamesModel.GameState getGameStateGeneral() {
        return switch (gameState) {
            case PLAYING, NOT_STARTED -> MinigamesModel.GameState.ONGOING;
            case GAME_WIN -> MinigamesModel.GameState.WON;
            case GAME_OVER -> MinigamesModel.GameState.LOST;
        };
    }
}
