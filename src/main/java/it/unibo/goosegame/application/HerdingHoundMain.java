package it.unibo.goosegame.application;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import it.unibo.goosegame.controller.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModelImpl;
import it.unibo.goosegame.view.general.GameMenuPanel;
import it.unibo.goosegame.view.minigames.herdinghound.impl.HerdingHoundView;
import it.unibo.goosegame.view.minigames.herdinghound.impl.RightPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

/**
 * Entry point for the Herding Hound minigame application.
 * Initializes the MVC and starts the user interface on the Swing thread.
 */
public final class HerdingHoundMain {
    private static final int GRID_SIZE = 31;
    private static final int LEFT_PANEL_WIDTH = 60;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

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
            final JFrame frame = new JFrame("Herding Hound");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            final GameMenuPanel menuPanel = new GameMenuPanel("HerdingHound", "Start Game", () -> {
                final int gridSize = GRID_SIZE;
                final HerdingHoundModelImpl model = new HerdingHoundModelImpl(gridSize);
                final HerdingHoundView view = new HerdingHoundView(model);
                final RightPanel rightPanel = new RightPanel(model);

                final JPanel leftPanel = new JPanel();
                leftPanel.setPreferredSize(new Dimension(LEFT_PANEL_WIDTH, 0));
                leftPanel.setBackground(Color.LIGHT_GRAY);

                frame.getContentPane().removeAll();
                frame.add(leftPanel, BorderLayout.WEST);
                frame.add(view, BorderLayout.CENTER);
                frame.add(rightPanel, BorderLayout.EAST);
                frame.revalidate();
                frame.repaint();

                final HerdingHoundController controller = new HerdingHoundController(model, view, frame, rightPanel);
                view.requestFocusInWindow();

                // Initial countdown, then the game starts
                view.startCountdown(() -> {
                    model.startGame();
                    controller.startGame();
                    view.requestFocusInWindow();
                });
            });
            frame.add(menuPanel, BorderLayout.CENTER);
            frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setVisible(true);
        });
    }
}
