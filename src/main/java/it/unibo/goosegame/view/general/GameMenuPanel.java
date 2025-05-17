package it.unibo.goosegame.view.general;

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
 * Reusable menu panel for minigames, with background image support.
 */
public final class GameMenuPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int BUTTON_FONT_SIZE = 28;
    private static final int COLOR_ALPHA = 180;
    private static final int COLOR_MENU_BLUE = 128;
    private static final int FALLBACK_IMG_SIZE = 32;

    private final transient Image backgroundImg;

    /**
     * Constructs a new menu panel with background image.
     * @param gameName The name of the minigame (e.g., "HerdingHound", "HonkMand")
     * @param startText The text for the start button
     * @param onStart Action to perform when the start button is pressed
     */
    public GameMenuPanel(final String gameName, final String startText, final Runnable onStart) {
        super(new GridBagLayout());
        this.backgroundImg = loadBackgroundImage(gameName);
        setOpaque(false);
        final JButton startButton = new JButton(startText);
        startButton.setFont(new Font("Arial", Font.BOLD, BUTTON_FONT_SIZE));
        startButton.addActionListener(e -> onStart.run());
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(startButton, gbc);
    }

    private Image loadBackgroundImage(final String gameName) {
        if (gameName == null) {
            return null;
        }
        final String path = "/img/" + gameName + "_menu.png";
        final URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            // fallback: colored background
            final BufferedImage img = new BufferedImage(FALLBACK_IMG_SIZE, FALLBACK_IMG_SIZE, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = img.createGraphics();
            g2.setColor(new Color(0, 0, COLOR_MENU_BLUE, COLOR_ALPHA));
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
