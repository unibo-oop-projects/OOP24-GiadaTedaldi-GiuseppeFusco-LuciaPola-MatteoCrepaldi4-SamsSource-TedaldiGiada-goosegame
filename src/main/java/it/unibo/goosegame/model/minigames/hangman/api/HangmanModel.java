package it.unibo.goosegame.model.minigames.hangman.api;
import it.unibo.goosegame.model.general.MinigamesModel;
/**
 * Interface for the Hangman game model.
 */
public interface HangmanModel extends MinigamesModel {
    /**
     * @return the current hidden word as a string.
     */
    String getHiddenWord();
    /**
     * @return the remaining attempts.
     */
    int getAttempts();
    /**
     * @return selectedWord, the word to guess.
     */
    String getSelectedWord();
    /**
     * @param letter the letter selected by the player
     * @return true if the letter is correct, false if not
     */
    boolean guess(char letter);
    /**
    * @return true if the player won, false otherwise.
    */
    boolean isWon();
    /**
    * @return true if the player lose, false otherwise.
    */
    boolean isLost();
}
