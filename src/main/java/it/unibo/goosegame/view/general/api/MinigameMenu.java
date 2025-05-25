package it.unibo.goosegame.view.general.api;

import javax.swing.JButton;
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
}
