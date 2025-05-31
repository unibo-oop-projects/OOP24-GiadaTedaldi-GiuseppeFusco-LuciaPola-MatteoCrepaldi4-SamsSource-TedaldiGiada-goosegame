package it.unibo.goosegame.controller.minigames.click_the_color.api;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * Controller class for the Click The Color minigame.
 */
public interface ClickTheColor {
    /**
     * Used to get the game result.
     *
     * @return whether the game has been won or lost
     */
    GameState getResult();
}
