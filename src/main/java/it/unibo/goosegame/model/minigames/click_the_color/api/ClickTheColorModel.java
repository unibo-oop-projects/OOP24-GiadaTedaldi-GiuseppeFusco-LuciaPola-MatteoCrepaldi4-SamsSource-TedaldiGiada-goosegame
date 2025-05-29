package it.unibo.goosegame.model.minigames.click_the_color.api;

import it.unibo.goosegame.model.general.MinigamesModel;

/**
 * Class for the Click The Color minigame model.
 */
public interface ClickTheColorModel extends MinigamesModel {
    /**
     * Updates the game logic.
     *
     * @return the index of the button to activate, -1 if no button is active
     */
    int update();

    /**
     * Signals to the view that a button has been clicked.
     *
     * @param index index of the button clicked by the user
     */
    void clicked(int index);

    /**
     * Getter method for the user score.
     *
     * @return the current user score
     */
    int getScore();
}
