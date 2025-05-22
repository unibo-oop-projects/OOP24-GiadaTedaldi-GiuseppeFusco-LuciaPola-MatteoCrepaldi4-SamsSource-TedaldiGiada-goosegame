package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;

/**
 * Entry point for the Herding Hound minigame application.
 * Initializes the MVC and starts the user interface on the Swing thread.
 */
public final class HerdingHoundMain {

    /**
     * Private constructor to prevent instantiation.
     */
    private HerdingHoundMain() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main method to launch the Herding Hound minigame.
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            new it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundFrame().setVisible(true);
        });
    }
}
