package it.unibo.goosegame.controller.minigames.rockpaperscissors.api;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Interface for the controller of the Rock-Paper-Scissors game.
 * It defines the method for handling a player's move.
 */
public interface RockPaperScissorsController {
    /**
     * @param playerChoice the player's choice (Rock, Paper, Scissors)
     */
    void playTurn(String playerChoice);
    /**
     * This method starts the game.
     */
    void startGame();
    /**
     * @return the current state of the game.
     */
    GameState getGameState();
}
