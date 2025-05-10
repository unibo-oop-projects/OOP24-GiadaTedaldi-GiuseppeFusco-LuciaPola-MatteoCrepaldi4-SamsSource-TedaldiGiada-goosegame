package it.unibo.goosegame.view;

import javax.swing.*;
import java.awt.*;

/**
 * Pannello riutilizzabile per la schermata di fine partita (vittoria/sconfitta).
 */
public class GameEndPanel extends JPanel {
    public GameEndPanel(String message, Runnable onClose) {
        super(new GridBagLayout());
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        JButton closeButton = new JButton("Chiudi");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        closeButton.addActionListener(e -> onClose.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10,10,10,10);
        add(label, gbc);
        gbc.gridy = 1;
        add(closeButton, gbc);
    }
}
