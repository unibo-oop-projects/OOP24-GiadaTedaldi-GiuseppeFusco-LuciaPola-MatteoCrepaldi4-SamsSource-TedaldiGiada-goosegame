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
        this.sequence.clear();
        this.currentStep = 0;
        this.userTurn = false;
        this.gameOver = false;
        this.gameWon = false;
        this.successfulRounds = 0;
    }

    public boolean isOver() {
        return this.gameOver || this.gameWon; //il gioco finisce se il gioco è perso o vinto
    }

    public boolean isGameWon() { //capisco se il gioco è vinto
        return this.gameWon;
    }

    public boolean isGameLost() { //capisco se il gioco è perso
        return this.gameOver;
    }

    public void addNewColor() { //aggiungo uno dei 4 clori a caso alla sequenza
        sequence.add(random.nextInt(4));
    }

    public List<Integer> getSequence() {//prendo la sequenza aumentata
        return this.sequence;
    }

    public int getCurrentStep() {
        return this.currentStep;
    }

    public void switchTurn(){
        this.userTurn = !this.userTurn;
        if (this.userTurn) {
            this.currentStep = 0;
        }
    }
    

    public boolean getUserTurn(){
        return this.userTurn;
    }


    public boolean checkInput(List<Integer> userSeq) {
        if (this.gameOver || this.gameWon) return false;

        if(this.sequence.get(currentStep).equals(userSeq.get(currentStep))){
            this.currentStep ++;
            if(isSequenceComplete()){
                this.successfulRounds++;
                if(successfulRounds >= MAX_ROUNDS){
                    this.gameWon = true;
                }return true;
            }return false;
        }else{
            gameOver = true;
            return false;
        }
    }

    public boolean isSequenceComplete() {
        return this.currentStep == this.sequence.size();
    }

    public int getSuccessfulRounds() {
        return successfulRounds;
    }
}


