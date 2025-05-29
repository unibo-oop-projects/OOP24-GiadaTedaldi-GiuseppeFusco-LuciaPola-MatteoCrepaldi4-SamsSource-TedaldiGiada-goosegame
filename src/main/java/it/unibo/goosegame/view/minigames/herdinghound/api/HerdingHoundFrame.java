package it.unibo.goosegame.view.minigames.herdinghound.api;

import java.awt.Component;

/**
 * API for the frame that displays the Herding Hound minigame.
 */
public interface HerdingHoundFrame {
    /**
     * Sets up the main game panels (center view and right panel).
     * @param view the main game view (center)
     * @param rightPanel the right panel (east)
     */
    void setupGamePanels(Component view, Component rightPanel);
}