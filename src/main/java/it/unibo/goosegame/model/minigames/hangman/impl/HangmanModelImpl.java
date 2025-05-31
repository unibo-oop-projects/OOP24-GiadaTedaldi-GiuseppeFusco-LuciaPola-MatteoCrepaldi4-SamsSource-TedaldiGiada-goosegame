package it.unibo.goosegame.model.minigames.hangman.impl;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

import it.unibo.goosegame.model.minigames.hangman.api.HangmanModel;

/**
 * This class implements the game logic for Hangman,
 * managing the hidden word, player guesses, remaining attempts,
 * and win/loss conditions.
 */
public class HangmanModelImpl implements HangmanModel {
    /** Maximum number of incorrect guesses allowed. */
    public static final int MAX_ATTEMPTS = 5;
    /**
     * StringBuilder used as a field to hold the current visible state of the word.
     * It is reset properly in resetGame() to avoid memory growth issues.
    */
    private final String[] words;
    private  char[] hiddenWord;
    private String selectedWord;
    private int attempts;
    private int  gameOver;
    private int  won;
    /**
     * @param words
     */
    public HangmanModelImpl(final String[] words) {
        this.words = Arrays.copyOf(words, words.length);
        resetGame();
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public String getHiddenWord() {
        return new String(hiddenWord);
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public int getAttempts() {
        return attempts;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public String getSelectedWord() {
        return selectedWord;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean guess(final char letter) {
        boolean correctGuess = false;
        for (int i = 0; i < selectedWord.length(); i++) {
            if (selectedWord.charAt(i) == letter) {
                hiddenWord[i] = letter;
                correctGuess = true;
            }
        } 
        if (!correctGuess) {
            attempts--;
        }
        if (new String(hiddenWord).equals(selectedWord)) {
            gameOver = 1;
            won = 1;
        } else if (attempts <= 0) {
            won = 0;
            gameOver = 1;
        }
        return correctGuess;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public final void resetGame() {
        attempts = MAX_ATTEMPTS;
        selectedWord = words[ThreadLocalRandom.current().nextInt(words.length)].toUpperCase(Locale.ROOT);
        hiddenWord = new char[selectedWord.length()];
        for (int i = 0; i < selectedWord.length(); i++) {
            hiddenWord[i] = "AEIOU".indexOf(selectedWord.charAt(i)) >= 0 ? '+' : '-';
        }
        gameOver = -1;
        won =  -1;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public GameState getGameState() {
        switch (won) {
            case -1 -> {
                return GameState.NOT_STARTED;
            }
            case 0 -> {
                return GameState.LOST;
            }
            case 1 -> {
                return GameState.WON;
            }
            default -> {
                return GameState.ONGOING;
            }
        }
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public String getName() {
        return "Hangman";
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isOver() {
        return gameOver == 1;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isWon() {
        return won == 1;
    }
    /**
    * {@inheritDoc}
    */
    @Override
    public boolean isLost() {
       return won == 0 && gameOver == 1;
    }
}
