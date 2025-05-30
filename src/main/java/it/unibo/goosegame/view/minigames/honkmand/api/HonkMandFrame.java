package it.unibo.goosegame.view.minigames.honkmand.api;

import javax.swing.JPanel;

/**
 * API for the frame that displays the HonkMand minigame.
 */
public interface HonkMandFrame {
    /**
     * Sets up the main game panel (center view).
     * @param view the main game view (center)
     */
    void setupGamePanel(JPanel view);
}

