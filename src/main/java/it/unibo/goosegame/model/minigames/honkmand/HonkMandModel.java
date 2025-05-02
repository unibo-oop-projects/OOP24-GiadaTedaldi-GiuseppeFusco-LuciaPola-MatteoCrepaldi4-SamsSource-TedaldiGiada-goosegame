package it.unibo.goosegame.model.minigames.honkmand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.goosegame.model.general.MinigamesModel;

public class HonkMandModel implements MinigamesModel {
    private List<Integer> sequence = new ArrayList<>();
    private int currentStep = 0;
    private boolean userTurn = false;
    private boolean gameOver = false;
    private boolean gameWon = false;
    private int successfulRounds = 0;
    private final int MAX_ROUNDS = 10;
    private Random random = new Random();

    public HonkMandModel() {
        resetGame();
        addNewColor();
    }    

    public void resetGame() {
        sequence.clear();
        currentStep = 0;
        userTurn = false;
        gameOver = false;
        gameWon = false;
        successfulRounds = 0;
    }

    public boolean isOver() {
        return gameOver || gameWon;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameLost() {
        return gameOver;
    }

    public void addNewColor() {
        sequence.add(random.nextInt(4));
        currentStep = 0;
        userTurn = false;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public boolean isUserTurn() {
        return userTurn;
    }

    public void setUserTurn(boolean userTurn) {
        this.userTurn = userTurn;
    }

    public boolean checkInput(int color) {
        if (gameOver || gameWon) return false;

        if (sequence.get(currentStep) == color) {
            currentStep++;
            if (currentStep == sequence.size()) {
                successfulRounds++;
                if (successfulRounds >= MAX_ROUNDS) {
                    gameWon = true;
                }
                return true;
            }
            return false;
        } else {
            gameOver = true;
            return false;
        }
    }

    public boolean isSequenceComplete() {
        return currentStep == sequence.size();
    }

    public int getSuccessfulRounds() {
        return successfulRounds;
    }
}


