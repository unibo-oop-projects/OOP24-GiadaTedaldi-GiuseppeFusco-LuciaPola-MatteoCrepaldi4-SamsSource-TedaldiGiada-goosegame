package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;
import it.unibo.goosegame.controller.honkmand.HonkMandController;

/**
 * Entry point for the HonkMand (Simon Game) application.
 * Initializes MVC and starts the user interface on the Swing thread.
 */
public final class HonkMandMain {
    private HonkMandMain() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            HonkMandController controller = new HonkMandController();
            controller.startGame();
        });
    }
}