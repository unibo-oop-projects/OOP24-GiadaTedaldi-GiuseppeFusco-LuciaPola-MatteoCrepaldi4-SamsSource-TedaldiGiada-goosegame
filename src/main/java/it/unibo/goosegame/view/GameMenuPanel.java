package it.unibo.goosegame.view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Reusable menu panel for minigames, with background image support.
 */
public class GameMenuPanel extends JPanel {
    private final Image backgroundImg;

    /**
     * Constructs a new menu panel with background image.
     * @param gameName The name of the minigame (e.g., "HerdingHound", "HonkMand")
     * @param startText The text for the start button
     * @param onStart Action to perform when the start button is pressed
     */
    public GameMenuPanel(String gameName, String startText, Runnable onStart) {
        super(new GridBagLayout());
        this.backgroundImg = loadBackgroundImage(gameName);
        setOpaque(false);
        JButton startButton = new JButton(startText);
        startButton.setFont(new Font("Arial", Font.BOLD, 28));
        startButton.addActionListener(e -> onStart.run());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(10,10,10,10);
        add(startButton, gbc);
    }

    private Image loadBackgroundImage(String gameName) {
        if (gameName == null) return null;
        String path = "/img/" + gameName + "_menu.png";
        URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            // fallback: colored background
            BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = img.createGraphics();
            g2.setColor(new Color(0, 0, 128, 180));
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
