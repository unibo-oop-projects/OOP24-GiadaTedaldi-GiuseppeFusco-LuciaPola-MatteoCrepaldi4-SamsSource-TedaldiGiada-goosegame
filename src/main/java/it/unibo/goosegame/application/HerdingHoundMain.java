package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HerdingHoundController;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.view.HerdingHoundView;
import it.unibo.goosegame.view.RightPanel;

import javax.swing.*;
import java.awt.*;

public class HerdingHoundMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Herding Hound");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel startPanel = new JPanel(new GridBagLayout());
            JButton startButton = new JButton("Avvia Gioco");
            startPanel.add(startButton);

            frame.add(startPanel, BorderLayout.CENTER);
            frame.setSize(800, 600); // Più largo per le bande laterali
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);
            frame.setVisible(true);

            startButton.addActionListener(e -> {
                int gridSize = 31;
                HerdingHoundModel model = new HerdingHoundModel(gridSize);
                HerdingHoundView view = new HerdingHoundView(model);
                RightPanel rightPanel = new RightPanel(model);

                JPanel leftPanel = new JPanel();
                leftPanel.setPreferredSize(new Dimension(60, 0));
                leftPanel.setBackground(Color.LIGHT_GRAY);

                frame.remove(startPanel);
                frame.add(leftPanel, BorderLayout.WEST);
                frame.add(view, BorderLayout.CENTER);
                frame.add(rightPanel, BorderLayout.EAST);
                frame.revalidate();
                frame.repaint();

                HerdingHoundController controller = new HerdingHoundController(model, view, frame, rightPanel);
                view.requestFocusInWindow();

                // Countdown iniziale, poi parte il gioco
                view.startCountdown(() -> {
                    controller.startGame();
                    view.requestFocusInWindow();
                });
            });
        });
    }
}
