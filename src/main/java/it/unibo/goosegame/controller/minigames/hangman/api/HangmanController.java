package it.unibo.goosegame.controller.minigames.hangman.api;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * This interface defines the contract for the Hangman game controller.
 * It allows the view to communicate user input to the controller.
 */
public interface HangmanController {
    /**
     * @param letter the letter pressed by the player
     */
    void onLetterPressed(char letter);
    /**
     * This method starts the game.
     */
    void startGame();
    /**
     * @return the current state of the game.
     */
    GameState getGameState();
}
