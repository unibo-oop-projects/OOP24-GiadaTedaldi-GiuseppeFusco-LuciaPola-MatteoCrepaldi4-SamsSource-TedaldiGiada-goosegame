package it.unibo.goosegame.view.general.api;

import javax.swing.JButton;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
/**
 * Interface for the MinigameMenuImpl.
 */
public interface MinigameMenu {
    /**
     * @return the start button.
     */
    JButton getStartButton();
    /**
     * Initialize view.
     */
    void initializeView();
    /**
     * @return the result of the game.
     */
    abstract GameState getGameState();
    /**
     * Closes the menu when called.
     */
    void dispose();
}
