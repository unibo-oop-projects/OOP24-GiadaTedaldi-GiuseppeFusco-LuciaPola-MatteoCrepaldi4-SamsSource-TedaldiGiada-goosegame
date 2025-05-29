package it.unibo.goosegame.application;

import javax.swing.SwingUtilities;
import it.unibo.goosegame.controller.herdinghound.HerdingHoundController;

/**
 * Entry point for the Herding Hound minigame application.
 * Initializes the MVC and starts the user interface on the Swing thread.
 */
public final class HerdingHoundMain {

    private HerdingHoundMain() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            HerdingHoundController controller = new HerdingHoundController();
            controller.startGame();
        });
    }
}
