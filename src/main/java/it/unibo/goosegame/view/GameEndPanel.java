package it.unibo.goosegame.view;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Reusable panel for the end-of-game screen (win/lose) with background image support.
 */
public final class GameEndPanel extends JPanel {
    private final Image backgroundImg;

    /**
     * Constructs a new end-of-game panel with background image.
     * @param message The message to display (e.g., "You Win!" or "You Lose!")
     * @param onClose Action to perform when the close button is pressed
     * @param gameName The name of the minigame (e.g., "HerdingHound", "HonkMand")
     * @param hasWon true if the game was won, false if lost
     */
    public GameEndPanel(final String message, final Runnable onClose, final String gameName, final boolean hasWon) {
        super(new GridBagLayout());
        this.backgroundImg = loadBackgroundImage(gameName, hasWon);
        setOpaque(false);
        final JLabel label = new JLabel(message);
        label.setFont(new Font("Arial", Font.BOLD, 32));
        label.setForeground(Color.WHITE);
        final JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 20));
        closeButton.addActionListener(e -> onClose.run());

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(label, gbc);
        gbc.gridy = 1;
        add(closeButton, gbc);
    }

    /**
     * Legacy constructor for backward compatibility (no image).
     * @param message The message to display
     * @param onClose Action to perform when the close button is pressed
     */
    public GameEndPanel(final String message, final Runnable onClose) {
        this(message, onClose, null, false);
    }

    private Image loadBackgroundImage(final String gameName, final boolean hasWon) {
        if (gameName == null) {
            return null;
        }
        final String outcome = hasWon ? "won" : "lost";
        final String path = "/img/" + gameName + "_" + outcome + ".png";
        final URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            // fallback: colored background
            final BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = img.createGraphics();
            g2.setColor(hasWon ? new Color(0, 128, 0, 180) : new Color(128, 0, 0, 180));
            g2.fillRect(0, 0, 32, 32);
            g2.dispose();
            return img;
        }
    }

    /**
     * Paints the background image or a fallback color, then the panel components.
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        if (backgroundImg != null) {
            g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(0, 0, 0, 180));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        super.paintComponent(g);
    }
}
