package it.unibo.goosegame.model.minigames.honkmand.impl;

import it.unibo.goosegame.model.minigames.honkmand.api.HonkMandModel;
import it.unibo.goosegame.utilities.Colors;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model class for the HonkMand minigame (a Simon-like memory game).
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
        this.sequence = new ArrayList<>();
        this.playerSequence = new ArrayList<>();
        this.level = 0;
        this.score = 0;
        this.random = new Random();
        this.gameState = GameState.NOT_STARTED;
    }

    /**
     * Starts a new game, resetting score and sequences.
     */
    @Override
    public void startGame() {
        this.sequence.clear();
        this.playerSequence.clear();
        this.level = 0;
        this.score = 0;
        this.gameState = GameState.ONGOING;
        this.nextRound();
    }

    /**
     * Advances to the next round, increasing the level and generating a new sequence.
     */
    @Override
    public void nextRound() {
        this.level++;
        this.playerSequence.clear();

        if (this.level > MAX_LEVEL) {
            this.gameState = GameState.WON;
            return;
        }
        this.generateNewSequence();
    }

    /**
     * Generates a new random sequence for the current level.
     */
    private void generateNewSequence() {
        this.sequence.clear();
        for (int i = 0; i < this.level; i++) {
            final int index = random.nextInt(Colors.values().length);
            this.sequence.add(Colors.values()[index]);
        }
    }

    /**
     * Checks the player's input against the sequence.
     * @param colorId the color chosen by the player
     * @return the result of the input
     */
    @Override
    public HonkMandModel.InputResult checkPlayerInput(final Colors colorId) {
        if (this.gameState != GameState.ONGOING) {
            return InputResult.GAME_OVER;
        }
        this.playerSequence.add(colorId);
        final int currentIndex = playerSequence.size() - 1;

        // Fix: check bounds before accessing sequence
        if (currentIndex >= this.sequence.size()) {
            gameState = GameState.LOST;
            return InputResult.GAME_OVER;
        }
        if (this.playerSequence.get(currentIndex).equals(this.sequence.get(currentIndex))) {
            if (this.playerSequence.size() == this.sequence.size()) {
                score++;
                if (this.level == MAX_LEVEL) {
                    this.gameState = GameState.WON;
                    return InputResult.GAME_WIN;
                }
                return InputResult.NEXT_ROUND;
            }
            return InputResult.CORRECT;
        } else {
            this.gameState = GameState.LOST;
            return InputResult.GAME_OVER;
        }
    }

    /**
     * Returns a defensive copy of the sequence to avoid exposing internal representation.
     * @return an unmodifiable copy of the sequence to be reproduced
     */
    @Override
    public List<Colors> getSequence() {
        return List.copyOf(this.sequence);
    }

    /**
     * Returns the current level.
     * @return the current level
     */
    @Override
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the current score.
     * @return the current score
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * Returns the current game state.
     * @return the current game state
     */
    @Override
    public GameState getGameState() {
        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
        this.startGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.gameState == GameState.LOST || this.gameState == GameState.WON;
    }

    @Override
    public String getName() {
        return "HonkMand";
    }
}
