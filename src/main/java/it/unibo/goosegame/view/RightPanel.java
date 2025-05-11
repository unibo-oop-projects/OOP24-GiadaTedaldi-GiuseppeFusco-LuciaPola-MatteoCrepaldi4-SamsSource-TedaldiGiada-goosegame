package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class RightPanel extends JPanel {
    private static final int PANEL_WIDTH = 200;
    private static final int IMAGE_SIZE = 120;
    private static final int TIMER_FONT_SIZE = 22;
    private final HerdingHoundModel model;
    private final Image awakeImage, alertImage, asleepImage;

    public RightPanel(HerdingHoundModel model) {
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Timer in alto a destra
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

        // Immagine stato cane centrata verticalmente
        final Image stateImage = switch (model.getDog().getState()) {
            case AWAKE -> awakeImage;
            case ALERT -> alertImage;
            default -> asleepImage;
        };
        int imgX = (getWidth() - IMAGE_SIZE) / 2;
        int imgY = (getHeight() - IMAGE_SIZE) / 2;
        g.drawImage(stateImage, imgX, imgY, IMAGE_SIZE, IMAGE_SIZE, this);
    }

    public void updatePanel() {
        repaint();
    }
}
