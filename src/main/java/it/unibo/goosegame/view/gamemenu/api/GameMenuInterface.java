package it.unibo.goosegame.view.gamemenu.api;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Defines the contract for the game menu's view component.
 * This interface abstracts the UI interactions related to the game's main menu,
 * including displaying screens and handling player input.
 */
public interface GameMenuInterface {
    /**
     * Display the instructions screen.
     */
    void showInstructions();
    /**
     * Displays the main menu screen.
     */
    void showMenu();
     /**
     * @return the player's name
     */
    String getPlayerName();
    /**
     * Clears the player's name.
     */
    void updatePlayerField();
    /**
     * @param text The text to set in the player name label
     */
    void updatePlayerLabel(String text);
    /**
     * Close window.
     */
    void dispose();
    /**
     * @param image The icon to be used on the button
     * @param w Width of the button
     * @param h Height of the button
     * @return The created button
     */
    JButton createButtonIcon(final ImageIcon image, final int w, final int h);
}
