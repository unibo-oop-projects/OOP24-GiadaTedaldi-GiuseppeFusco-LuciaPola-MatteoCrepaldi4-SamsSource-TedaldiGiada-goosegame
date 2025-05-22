package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.honkmand.HonkMandController;
import it.unibo.goosegame.model.minigames.honkmand.impl.HonkMandModelImpl;
import it.unibo.goosegame.view.general.GameMenuPanel;
import it.unibo.goosegame.view.minigames.honkmand.impl.HonkMandViewImpl;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;

/**
 * Entry point for the HonkMand (Simon Game) application.
 * Initializes MVC and starts the user interface on the Swing thread.
 */
public final class HonkMandMain {
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

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
            final JFrame frame = new JFrame("HonkMand");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);

            // Menu panel with background image
            final GameMenuPanel menuPanel = new GameMenuPanel("HonkMand", "Start Game", () -> {
                frame.getContentPane().removeAll();
                final HonkMandViewImpl view = new HonkMandViewImpl();
                view.setFrameRef(frame);
                frame.add(view, BorderLayout.CENTER);
                frame.revalidate();
                frame.repaint();
                final HonkMandModelImpl model = new HonkMandModelImpl();
                new HonkMandController(model, view);
            });
            frame.add(menuPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
