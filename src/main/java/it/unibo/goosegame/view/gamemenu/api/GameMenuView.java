package it.unibo.goosegame.view.gamemenu.api;
/**
 * Defines the contract for the game menu's view component.
 * This interface abstracts the UI interactions related to the game's main menu,
 * including displaying screens and handling player input.
 */
public interface GameMenuView {
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
}
