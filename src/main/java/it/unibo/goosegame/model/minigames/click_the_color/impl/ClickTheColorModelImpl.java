package it.unibo.goosegame.model.minigames.click_the_color.impl;

import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;

import java.util.Random;

/**
 * Implementation of {@link ClickTheColorModel}.
 */
public final class ClickTheColorModelImpl implements ClickTheColorModel {
    private static final int MAX_WAIT_TIME = 50;       //  The max time the program can wait for extraction (5 seconds)
    private static final int MAX_PLAYER_DELAY = 7;     //  The time the player has to respond to the game (0.7 seconds)
    private static final int ROUNDS = 10;              //  Number of rounds
    private static final int LOW_POINTS = 5;
    private static final int HIGH_POINTS = 10;
    private static final int MIN_SCORE = 25;
    private static final double EXTRACTION_WEIGHT = 0.01;

    private Random random;          //  Random class
    private boolean gameReady;      //  True if the game is waiting for the right time to show the next button
    private boolean waitingForUser; //  True if the button is showing and the logic is waiting for the user input
    private int nextButton;         //  Contains the index of the next button to enable
    private int delay;              //  Measures the time the program has spent waiting for the time to show the button
    private int playerDelay;        //  Measures the time the player is taking to click the right button
    private int playedRounds;       //  Number of rounds played
    private int score;              //  Player score

    /**
     * Implementation of the {@link ClickTheColorModel} interface.
     */
    public ClickTheColorModelImpl() {
        this.random = new Random();
        nextButton();
    }

    @Override
    public void resetGame() {
        this.random = new Random();
        this.gameReady = false;
        this.waitingForUser = false;
        this.nextButton = 0;
        this.delay = 0;
        this.playerDelay = 0;
    }

    @Override
    public String getName() {
        return "Click the color";
    }

    @Override
    public GameState getGameState() {
        if (!isOver()) {
            return GameState.ONGOING;
        } else {
            if (score >= MIN_SCORE) {
                return GameState.WON;
            }

            return GameState.LOST;
        }
    }

    @Override
    public boolean isOver() {
        return this.playedRounds >= ROUNDS;
    }

    @Override
    public int update() {
        //  If the game isn't ready turn off all buttons and get next round
        if (!gameReady) {
            nextButton();
            return -1;
        }

        //  If the player waited too much, get the next round
        if (playerDelay > MAX_PLAYER_DELAY) {
            nextButton();
            playedRounds++;
            score -= HIGH_POINTS;
            return -1;
        }

        //  If waiting for player to click, return the enabled button and update the player delay
        if (waitingForUser) {
            playerDelay++;
            return nextButton;
        }

        //  If the max delay is reached or the random is within range, make the game show the button
        if (Math.random() < EXTRACTION_WEIGHT || delay == MAX_WAIT_TIME) {
            waitingForUser = true;
            return nextButton;
        }

        this.delay++;
        return -1;
    }

    @Override
    public void clicked(final int index) {
        //  If the player clicks a button when no button is active or click the wrong button decrease the score
        if (!this.waitingForUser || index != this.nextButton) {
            this.score -= LOW_POINTS;
            return;
        }

        score += HIGH_POINTS;
        playedRounds++;
        nextButton();
    }

    @Override
    public int getScore() {
        return score;
    }

    /**
     * Utility function to extract the next button and reset the round variables.
     */
    private void nextButton() {
        this.nextButton = random.nextInt(4);
        this.delay = 0;
        this.playerDelay = 0;

        this.gameReady = true;
        this.waitingForUser = false;
    }
}
