package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.GameMenuPanel;
import it.unibo.goosegame.view.HerdingHoundView;
import it.unibo.goosegame.view.RightPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the Herding Hound minigame application.
 * Initializes the MVC and starts the user interface on the Swing thread.
 */
public class HerdingHoundMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Herding Hound");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            GameMenuPanel menuPanel = new GameMenuPanel("HerdingHound", "Start Game", () -> {
                int gridSize = 31;
                HerdingHoundModel model = new HerdingHoundModel(gridSize);
                HerdingHoundView view = new HerdingHoundView(model);
                RightPanel rightPanel = new RightPanel(model);

                JPanel leftPanel = new JPanel();
                leftPanel.setPreferredSize(new Dimension(60, 0));
                leftPanel.setBackground(Color.LIGHT_GRAY);

                frame.getContentPane().removeAll();
                frame.add(leftPanel, BorderLayout.WEST);
                frame.add(view, BorderLayout.CENTER);
                frame.add(rightPanel, BorderLayout.EAST);
                frame.revalidate();
                frame.repaint();

                HerdingHoundController controller = new HerdingHoundController(model, view, frame, rightPanel);
                view.requestFocusInWindow();

                // Initial countdown, then the game starts
                view.startCountdown(() -> {
                    controller.startGame();
                    view.requestFocusInWindow();
                });
            });
            frame.add(menuPanel, BorderLayout.CENTER);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setVisible(true);
        });
    }
}
