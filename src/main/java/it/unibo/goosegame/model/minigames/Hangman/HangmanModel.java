package it.unibo.goosegame.model.minigames.Hangman;

import java.util.Random;

import it.unibo.goosegame.model.general.MinigamesModel;

public class HangmanModel implements MinigamesModel{
    public String selectedWord;
    private final StringBuilder hiddenWord;
    private final String[] words;
    private int attempts;
    private boolean  gameOver = false;
    private boolean  won = false;

    @SuppressWarnings("OverridableMethodCallInConstructor")
    public HangmanModel(String[] words) {
        this.words = words;
        hiddenWord = new StringBuilder();
        resetGame();
    }

    public String getHiddenWord() {
        return hiddenWord.toString();
    }

    public int getAttempts() {
        return attempts;
    }

    public String getSelectedWord() {
        return selectedWord;
    }

    public boolean guess(char letter) {
         boolean correctGuess = false;

            for(int i = 0; i < selectedWord.length(); i++) {
                if(selectedWord.charAt(i) == letter) {
                    hiddenWord.setCharAt(i, letter);
                    correctGuess = true;
                }
            } 
            if(!correctGuess) {
                attempts--;
            }

            if(hiddenWord.toString().equals(selectedWord)) {
                gameOver = true;
                won = true;
            } else if(attempts <= 0) {
                gameOver = true;
            }
            return correctGuess;
    }

    @Override
    public void resetGame() {
        attempts = 5;
        selectedWord = words[new Random().nextInt(words.length)].toUpperCase();
        hiddenWord.setLength(0);
        for(char c : selectedWord.toCharArray())
            hiddenWord.append("AEIOU".indexOf(c) >= 0 ? "+" : "-");
        gameOver = false;
        won =  false;
    }

    @Override
    public int getResult() {
        return won ? 1 : 0;
    }

    @Override
    public String getName() {
        return "Hangman";
    }

    @Override
    public boolean isOver() {
        return gameOver;
    }

    public boolean isWon() {
        return won;
    }

    public boolean isLost() {
        return !won && gameOver;
    }
}