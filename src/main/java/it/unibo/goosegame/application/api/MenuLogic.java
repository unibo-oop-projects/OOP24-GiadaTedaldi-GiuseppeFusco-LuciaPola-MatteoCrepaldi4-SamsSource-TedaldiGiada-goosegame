package it.unibo.goosegame.application.api;
/**
 *  Defines the core logic for the game menu.
 * This interface provides the main operations for handling user interactions,
 * such as starting the game, adding players, and showing instructions.
 */
public interface MenuLogic {
    /**
     * 
     */
    void startGame();
    /**
     * Adds a new player to the game. If the player name is valid and does not exceed the maximum number of players, 
     * it is added to the list. Displays an error message if the player limit is reached or if the player name 
     * already exists in the list.
     */
    void addPlayer();
    /**
     * Displays the instructions on how to play the game.
     */
    void showInstructions(); 
     /**
     * @return the number of players currently added
     */
    int getPlayersCount();
}
