package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;

/**
 * Entry point for the HonkMand (Simon Game) application.
 * Initializes MVC and starts the user interface on the Swing thread.
 */
public final class HonkMandMain {
    /**
     * Private constructor to prevent instantiation.
     */
    private HonkMandMain() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main method that starts the application.
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            new it.unibo.goosegame.view.minigames.honkmand.impl.HonkMandFrame().setVisible(true);
        });
    }
}
