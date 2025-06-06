package it.unibo.goosegame.model.minigames.hangman;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.minigames.hangman.api.HangmanModel;
import it.unibo.goosegame.model.minigames.hangman.impl.HangmanModelImpl;
/**
 * This class implements the test for the HangmanModel class.
 */
class TestHangmanModelImpl {
    private static final int ATTEMPTS = 5;

    private HangmanModel model;

    /**
     * Set up the HangmanModel before each test.
     * Initializes the model with a single word "APPLE".
     */
    @BeforeEach
    public void setUp() {
        this.model = new HangmanModelImpl(new String[]{"APPLE"});
    }

    /**
     * Test the initial state of the Hangman game.
     * This test checks if the hidden word has the correct length (5),
     * if it contains the correct placeholder symbols for letters ('+' and '-'),
     * and if the number of attempts is set correctly.
     */
    @Test
    void testInitialState() {
        final String hiddenWord = this.model.getHiddenWord();
        assertEquals(ATTEMPTS, hiddenWord.length());
        assertTrue(hiddenWord.contains("+"));
        assertTrue(hiddenWord.contains("-"));
        assertEquals(ATTEMPTS, this.model.getAttempts(), "The hidden word length should be 5");
    }

    /**
     * Test if a correct guess reveals the correct letter in the hidden word.
     * This test checks if guessing the letter 'P' reveals the corresponding positions in the word.
     */
    @Test
    void testCorrectGuessRevealsLetter() {
        final boolean result = this.model.guess('P');
        assertTrue(result);
        assertEquals("+PP-+", this.model.getHiddenWord());
    }

    /**
     * Test if an incorrect guess decreases the number of attempts.
     * This test ensures that an incorrect guess (like 'Z') reduces the remaining attempts.
     */
    @Test
    void testIncorrectGuessRevealsLetter() {
        final int before = this.model.getAttempts();
        final boolean result = this.model.guess('Z');
        assertFalse(result);
        assertEquals(before - 1, this.model.getAttempts(), "Attempts should decrease by 1 after an incorrect guess");
    }

    /**
     * Test if the game is won after correctly guessing the word.
     * This test simulates a game with the word 'A' and ensures that the game is won after guessing 'A'.
     */
    @Test
    void testGameWin() {
        this.model = new HangmanModelImpl(new String[]{"A"});
        final String hidden = this.model.getHiddenWord();
        assertEquals("+", hidden, "The hidden word should be '+' for the word 'A'");
        this.model.guess('A');
        assertTrue(model.isOver(), "The game should be over after guessing the word correctly");
        assertTrue(model.isWon(), "The game should be won after guessing the correct letter");
    }

    /**
     * Test if the game is lost after 5 incorrect guesses.
     * This test simulates a scenario where the player makes 5 incorrect guesses,
     * and ensures that the game is lost and the number of attempts is exhausted.
     */
    @Test
    void testGameLose() {
        this.model = new HangmanModelImpl(new String[]{"B"});
        for (int i = 0; i < ATTEMPTS; i++) {
            this.model.guess('Z');
        }
        assertTrue(this.model.isOver(), "The game should be over after 5 incorrect guesses");
        assertFalse(this.model.isWon(), "The game should not be won after 5 incorrect guesses");
        assertTrue(this.model.isLost(), "The game should be lost after 5 incorrect guesses");
    }
}
