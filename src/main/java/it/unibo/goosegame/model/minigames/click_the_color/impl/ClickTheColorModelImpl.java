package it.unibo.goosegame.model.minigames.click_the_color.impl;

import it.unibo.goosegame.model.minigames.click_the_color.api.ClickTheColorModel;
import it.unibo.goosegame.utilities.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ClickTheColorModelImpl implements ClickTheColorModel {
    private final int MAX_WAIT_TIME = 50;       //  After how many update cycles the logic forces the button to be shown (5 seconds)
    private final int MAX_PLAYER_DELAY = 10;    //  The time the player has to respond to the game (1 second)
    private final int ROUNDS = 5;

    private Random random;          //  Random class
    private boolean gameReady;      //  True if the game is waiting for the right time to show the next button
    private boolean waitingForUser; //  True if the button is showing and the logic is waiting for the user input
    private int nextButton;         //  Contains the index of the next button to enable
    private int delay;              //  Measures the time the program has spent waiting for the time to show the button
    private int playerDelay;        //  Measures the time the player is taking to click the right button
    private int playedRounds;
    private int score;

    public ClickTheColorModelImpl() {
        resetGame();
        getNextButton();
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
    public int getResult() {
        return 0;
    }

    @Override
    public String getName() {
        return "Click the color";
    }

    @Override
    public boolean isOver() {
        return this.playedRounds >= ROUNDS;
    }

    @Override
    public int update() {
        //  If the game isn't ready turn off all buttons and get next round
        if (!gameReady) {
            getNextButton();
            return -1;
        }

        //  If the player waited too much, get the next round
        if (playerDelay > MAX_PLAYER_DELAY) {
            getNextButton();
            playedRounds++;
            score -= 10;
            return -1;
        }

        //  If waiting for player to click, return the enabled button and update the player delay
        if (waitingForUser) {
            playerDelay++;
            return nextButton;
        }

        //  If the max delay is reached, force the game to show the button
        if (delay == MAX_WAIT_TIME) {
            waitingForUser = true;
            return nextButton;
        }

        //  Extracts if the program will now show the button or not
        if(Math.random() < 0.01) {
            //  The program wants to show the button

            waitingForUser = true;
            return nextButton;
        }

        this.delay++;
        return -1;
    }

    @Override
    public void clicked(int index) {
        //  If the player clicks a button when no button is active or click the wrong button decrease the score
        if (!this.waitingForUser || index != this.nextButton) {
            this.score -= 5;
            return;
        }

        score += 10;
        playedRounds++;
        getNextButton();
    }

    @Override
    public int getScore() {
        return score;
    }

    private void getNextButton() {
        this.nextButton = random.nextInt(4);
        this.delay = 0;
        this.playerDelay = 0;

        this.gameReady = true;
        this.waitingForUser = false;
    }
}
