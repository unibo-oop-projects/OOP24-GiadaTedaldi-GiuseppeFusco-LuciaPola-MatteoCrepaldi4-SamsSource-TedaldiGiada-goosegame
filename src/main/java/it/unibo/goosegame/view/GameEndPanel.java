package it.unibo.goosegame.view;

import javax.swing.*;
import java.awt.*;

/**
 * Reusable panel for the end-of-game screen (win/lose).
 */
public class GameEndPanel extends JPanel {
    /**
     * Constructs a new end-of-game panel.
     * @param message The message to display (e.g., "You Win!" or "You Lose!")
     * @param onClose Action to perform when the close button is pressed
     */
    public GameEndPanel(String message, Runnable onClose) {
        super(new GridBagLayout());
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        closeButton.addActionListener(e -> onClose.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10,10,10,10);
        add(label, gbc);
        gbc.gridy = 1;
        add(closeButton, gbc);
    }
}
