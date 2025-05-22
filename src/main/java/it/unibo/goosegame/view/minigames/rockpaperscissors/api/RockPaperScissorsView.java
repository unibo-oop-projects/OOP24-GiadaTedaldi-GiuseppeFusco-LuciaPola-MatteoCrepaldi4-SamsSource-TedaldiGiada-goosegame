package it.unibo.goosegame.view.minigames.rockpaperscissors.api;

import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

/**
 * Interface for the Rock-Paper-Scissors game view.
 * Defines the operations the controller can perform on the view.
 */
public interface RockPaperScissorsView {
    /**
     * Initializes the graphical components of the Hangman view.
     */
    void initializeView();
    /**
     * @param icon the new image representing the computer's choice
     */
    void updateComputerChoice(ImageIcon icon);
    /**
     * @param icon the new image representing the player's choice
     */
    void updatePlayerChoice(ImageIcon icon);
    /**
     * @param score new computer's score
     */
    void updateComputerScore(int score);
    /**
     * @param score new player's score
     */
    void updatePlayerScore(int score);
    /**
     * @param result the result text to show
     */
    void updateResult(String result);
    /**
     * @param l listener the ActionListener to register
     */
    void addRockListener(ActionListener l);
    /**
     * @param l listener the ActionListener to register
     */
    void addPaperListener(ActionListener l);
    /**
     * @param l listener the ActionListener to register
     */
    void addScissorsListener(ActionListener l);
    /**
     * Enable all the buttons.
     */
    void enableAllButtons();
}
