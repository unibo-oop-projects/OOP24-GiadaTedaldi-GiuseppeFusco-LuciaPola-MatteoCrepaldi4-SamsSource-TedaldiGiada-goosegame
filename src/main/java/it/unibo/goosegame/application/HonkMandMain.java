package it.unibo.goosegame.application;

import it.unibo.goosegame.controller.HonkMandController;
import it.unibo.goosegame.model.minigames.honkmand.HonkMandModel;
import it.unibo.goosegame.view.HonkMandView;

import javax.swing.*;
import java.awt.*;

/**
 * Entry point for the HonkMand (Simon Game) application.
 * Initializes MVC and starts the user interface on the Swing thread.
 */
public class HonkMandMain {
    /**
     * Main method that starts the application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("HonkMand");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setResizable(true);

            HonkMandView view = new HonkMandView();
            view.setFrameRef(frame);
            frame.add(view, BorderLayout.CENTER);

            frame.setVisible(true);

            HonkMandModel model = new HonkMandModel();
            new HonkMandController(model, view);
        });
    }
}
