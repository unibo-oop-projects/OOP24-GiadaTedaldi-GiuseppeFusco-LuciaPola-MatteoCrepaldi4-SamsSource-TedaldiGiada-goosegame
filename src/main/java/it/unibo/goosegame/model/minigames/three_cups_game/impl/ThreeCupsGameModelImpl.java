package it.unibo.goosegame.model.minigames.three_cups_game.impl;

import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;

import java.util.Random;

/**
 * Implementation of {@link ThreeCupsGameModel}.
 */
public final class ThreeCupsGameModelImpl implements ThreeCupsGameModel {
    private static final int ROUNDS = 10;
    private static final int MIN_VICTORIES = 3;

    private int correctCup;
    private int victories;
    private int turns;
    private Random rand;

    /**
     * Constructor for the minigame model.
     */
    public ThreeCupsGameModelImpl() {
        this.rand = new Random();
        startNextRound();
    }

    private void startNextRound() {
        this.correctCup = rand.nextInt(3);
    }

    @Override
    public boolean makeChoice(final int choice) {
        boolean guessedCorrectly = false;
        startNextRound();

        if (this.correctCup == choice) {
            victories++;
            guessedCorrectly = true;
        }

        turns++;
        return guessedCorrectly;
    }

    @Override
    public void resetGame() {
        this.rand = new Random();
        this.victories = 0;
        this.turns = 0;

        startNextRound();
    }

    @Override
    public String getName() {
        return "Three Cups Game";
    }

    @Override
    public GameState getGameState() {
        if (!isOver()) {
            return GameState.ONGOING;
        } else {
            if (victories >= MIN_VICTORIES) {
                return GameState.WON;
            }

            return GameState.LOST;
        }
    }

    @Override
    public boolean isOver() {
        return turns == ROUNDS;
    }

    @Override
    public String getStatus() {
        return "Correct guesses: " + this.victories + " Turn: " + this.turns + "/" + this.ROUNDS;
    }
}
