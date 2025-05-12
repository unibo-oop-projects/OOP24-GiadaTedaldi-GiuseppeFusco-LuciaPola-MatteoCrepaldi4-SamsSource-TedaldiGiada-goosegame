package it.unibo.goosegame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Reusable panel for the end-of-game screen (win/lose) with background image support.
 */
public class GameEndPanel extends JPanel {
    private final Image backgroundImg;

    /**
     * Constructs a new end-of-game panel with background image.
     * @param message The message to display (e.g., "You Win!" or "You Lose!")
     * @param onClose Action to perform when the close button is pressed
     * @param gameName The name of the minigame (e.g., "HerdingHound", "HonkMand")
     * @param hasWon true if the game was won, false if lost
     */
    public GameEndPanel(String message, Runnable onClose, String gameName, boolean hasWon) {
        super(new GridBagLayout());
        this.backgroundImg = loadBackgroundImage(gameName, hasWon);
        setOpaque(false);
        JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(Color.WHITE);
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        closeButton.addActionListener(e -> onClose.run());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10,10,10,10);
        add(label, gbc);
        gbc.gridy = 1;
        add(closeButton, gbc);
    }

    /**
     * Legacy constructor for backward compatibility (no image).
     */
    public GameEndPanel(String message, Runnable onClose) {
        this(message, onClose, null, false);
    }

    private Image loadBackgroundImage(String gameName, boolean hasWon) {
        if (gameName == null) return null;
        String outcome = hasWon ? "won" : "lost";
        String path = "/img/" + gameName + "_" + outcome + ".png";
        URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            // fallback: colored background
            BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setColor(hasWon ? new Color(0, 128, 0, 180) : new Color(128, 0, 0, 180));
            g2.fillRect(0, 0, 32, 32);
            g2.dispose();
            return img;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(0,0,0,180));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}
