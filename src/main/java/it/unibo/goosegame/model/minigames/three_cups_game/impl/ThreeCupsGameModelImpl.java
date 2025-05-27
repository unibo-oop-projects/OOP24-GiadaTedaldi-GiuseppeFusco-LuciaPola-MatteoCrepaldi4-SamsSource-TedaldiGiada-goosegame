package it.unibo.goosegame.model.minigames.three_cups_game.impl;

import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;

import java.util.Optional;
import java.util.Random;

/**
 * Implementation of {@link ThreeCupsGameModel}.
 */
public final class ThreeCupsGameModelImpl implements ThreeCupsGameModel {
    private static final int ROUNDS = 10;
    private static final int MIN_VICTORIES = 3;

    private Random random;
    private boolean roundOver;
    private int userChoice;
    private int score;
    private int rightChoice;
    private int playedRounds;

    /**
     * Constructor for the Three Cups Game implementation class.
     */
    public ThreeCupsGameModelImpl() {
        random = new Random();
        roundOver = false;
        score = 0;
        playedRounds = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update() {
        if (roundOver) {
            return rightChoice;
        }

        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clicked(final int index) {
        userChoice = index;

        if (!roundOver) {
            roundOver = true;
            return;
        }

        if (rightChoice == userChoice) {
            score++;
        }

        roundOver = false;
        nextRound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRoundNumber() {
        return playedRounds + 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getVictories() {
        return score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Boolean> getLastChoice() {
        if (!roundOver) {
            return Optional.empty();
        }

        return Optional.of(rightChoice == userChoice);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMaxRounds() {
        return ROUNDS;
    }

    /**
     * Utility method used to start the next round of the game.
     */
    private void nextRound() {
        rightChoice = random.nextInt(3);
        playedRounds++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
        random = new Random(0);
        score = 0;
        playedRounds = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return playedRounds >= ROUNDS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Three Cups Game";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWaiting() {
        return this.roundOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        if (playedRounds >= ROUNDS) {
            if (score < MIN_VICTORIES) {
                return GameState.LOST;
            } else {
                return GameState.WON;
            }
        }

        return GameState.ONGOING;
    }
}
