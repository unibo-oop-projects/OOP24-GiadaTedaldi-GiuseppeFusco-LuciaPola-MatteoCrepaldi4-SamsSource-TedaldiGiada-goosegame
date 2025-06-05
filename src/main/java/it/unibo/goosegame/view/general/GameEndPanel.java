package it.unibo.goosegame.view.general;

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
 * Panel for displaying the end-of-game screen with a background image or fallback color.
 * <p>
 * Shows a message (win/lose), a close button, and optionally a themed background for the minigame.
 * Supports both modern and legacy construction for backward compatibility.
 */
public final class GameEndPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int BUTTON_FONT_SIZE = 20;
    private static final int COLOR_ALPHA = 180;
    private static final int COLOR_GREEN = 128;
    private static final int COLOR_RED = 128;
    private static final int FALLBACK_IMG_SIZE = 32;
    private static final int LABEL_FONT_SIZE = 32;

    private final transient Image backgroundImg;

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
        label.setOpaque(true);
        label.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
        label.setForeground(Color.WHITE);
        label.setBackground(Color.BLACK);
        final JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, BUTTON_FONT_SIZE));
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
        final String path = "/img/minigames/endpanel/" + gameName + "_" + outcome + ".png";
        final URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            // fallback: colored background
            final BufferedImage img = new BufferedImage(FALLBACK_IMG_SIZE, FALLBACK_IMG_SIZE, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = img.createGraphics();
            g2.setColor(hasWon ? new Color(0, COLOR_GREEN, 0, COLOR_ALPHA) : new Color(COLOR_RED, 0, 0, COLOR_ALPHA));
            g2.fillRect(0, 0, FALLBACK_IMG_SIZE, FALLBACK_IMG_SIZE);
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
