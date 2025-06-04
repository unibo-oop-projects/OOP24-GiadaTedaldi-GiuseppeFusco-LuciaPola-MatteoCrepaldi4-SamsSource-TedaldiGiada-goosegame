package it.unibo.goosegame.controller.minigames.three_cups_game.api;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Controller for the Three Cups Game minigame.
 */
public interface ThreeCupsGame {
    /**
     * Used to get the game result.
     */
    GameState getGameState();
}
