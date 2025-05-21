package it.unibo.goosegame.view.minigames.hangman.api;

import it.unibo.goosegame.controller.minigames.hangman.api.HangmanController;

/**
 * Interface for the Hangman game view.
 * Defines methods for updating the UI and interacting with the controller.
 */
public interface HangmanView {
    /**
     * Initializes the graphical components of the Hangman view.
     */
    void initializeView();
    /**
     * @param word  The update word to guess.
     */
    void updateWord(String word);
    /**
     * @param attempts Attempts remaining.
     */
    void updateImage(int attempts);
    /**
     * @param letter The letter to deactivate on the keyboard.
     */
    void disableButton(char letter);
    /**
     * The class disable all the buttons.
     */
    void disableAllButton();
    /**
     * @param controller 
     */
    void setController(HangmanController controller);
}
