package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Panel on the right side of the Herding Hound minigame.
 * Displays the timer and the dog's state image.
 */
public final class RightPanel extends JPanel {
    private static final int PANEL_WIDTH = 200;
    private static final int IMAGE_SIZE = 120;
    private static final int TIMER_FONT_SIZE = 22;
    private final HerdingHoundModel model;
    private final Image awakeImage, alertImage, asleepImage;

    /**
     * Constructs a RightPanel.
     * @param model the game model
     */
    public RightPanel(final HerdingHoundModel model) {
        this.model = model;
        setPreferredSize(new Dimension(PANEL_WIDTH, 0));
        setBackground(Color.LIGHT_GRAY);
        this.awakeImage = loadImage("/img/dog_awake.png");
        this.alertImage = loadImage("/img/dog_alert.png");
        this.asleepImage = loadImage("/img/dog_asleep.png");
    }

    private Image loadImage(final String path) {
        final URL resource = getClass().getResource(path);
        if (resource != null) {
            return new ImageIcon(resource).getImage();
        } else {
            final BufferedImage img = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
            final Graphics2D g2 = img.createGraphics();
            g2.setColor(Color.RED);
            g2.fillRect(0, 0, 32, 32);
            g2.dispose();
            return img;
        }
    }

    /**
     * Paints the timer and the dog's state image.
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        // Timer in the top right
        final long remMs = model.getRemainingTime();
        final long sec = remMs / 1000;
        final String text = String.format("Time %02d:%02d", sec / 60, sec % 60);

        g.setColor(Color.DARK_GRAY);
        final Font font = new Font("Arial", Font.BOLD, TIMER_FONT_SIZE);
        g.setFont(font);
        final FontMetrics fm = g.getFontMetrics();
        final int textWidth = fm.stringWidth(text);
        final int tx = getWidth() - textWidth - 10;
        final int ty = fm.getAscent() + 20;
        g.drawString(text, tx, ty);

        // Dog state image centered vertically
        final Image stateImage = switch (model.getDog().getState()) {
            case AWAKE -> awakeImage;
            case ALERT -> alertImage;
            default -> asleepImage;
        };
        final int imgX = (getWidth() - IMAGE_SIZE) / 2;
        final int imgY = (getHeight() - IMAGE_SIZE) / 2;
        g.drawImage(stateImage, imgX, imgY, IMAGE_SIZE, IMAGE_SIZE, this);
    }

    /**
     * Updates the panel (repaints it).
     */
    public void updatePanel() {
        repaint();
    }
}
