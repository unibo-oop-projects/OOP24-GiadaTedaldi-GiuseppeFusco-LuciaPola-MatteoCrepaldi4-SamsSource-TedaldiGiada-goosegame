package it.unibo.goosegame.controller.minigames.rockpaperscissors.api;
/**
 * Interface for the controller of the Rock-Paper-Scissors game.
 * It defines the method for handling a player's move.
 */
public interface RockPaperScissorsController {
    /**
     * @param playerChoice the player's choice (Rock, Paper, Scissors)
     */
    void playTurn(String playerChoice);
}
