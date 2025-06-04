package it.unibo.goosegame.controller.minigames.three_cups_game.api;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Controller for the Three Cups Game minigame.
 */
public interface ThreeCupsGame {
    /**
     * Returns the current game state of the minigame.
     *
     * @return the current game state
     */
    GameState getGameState();
}
