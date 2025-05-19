package it.unibo.goosegame.model.minigames.three_cups_game.impl;

import it.unibo.goosegame.model.minigames.three_cups_game.api.ThreeCupsGameModel;

import java.util.Random;

public class ThreeCupsGameModelImpl implements ThreeCupsGameModel {
    private final int ROUNDS = 10;

    private int correctCup;
    private int victories;
    private int turns;
    private Random rand;


    public ThreeCupsGameModelImpl() {
        this.rand = new Random();

        startNextRound();
    }

    @Override
    public void startNextRound() {
        this.correctCup = rand.nextInt(3);
    }

    @Override
    public boolean makeChoice(int choice) {
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
    public int getResult() {
        return this.victories;
    }

    @Override
    public String getName() {
        return "Three Cups Game";
    }

    @Override
    public boolean isOver() {
        return turns == ROUNDS;
    }

    // === GETTERS ===

    public String getStatus() {
        return "Correct guesses: " + this.victories + " Turn: " + this.turns + "/" + this.ROUNDS;
    }
}
