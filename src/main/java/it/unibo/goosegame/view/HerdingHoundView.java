package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.utilities.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.Objects;

/**
 * View per il minigioco Herding Hound.
 * Si occupa solo della presentazione grafica.
 */
public class HerdingHoundView extends JPanel {
    private static final long serialVersionUID = 1L;
    private static final int DEFAULT_SIZE = 600;
    private static final int IMAGE_SIZE = 200;
    private static final int TIMER_FONT_SIZE = 16;
    private static final Color BACKGROUND_COLOR = new Color(50, 50, 50);
    private static final Color GRID_COLOR = Color.GRAY;
    private static final Color VISIBLE_AREA_COLOR = Color.LIGHT_GRAY;
    private static final Color DOG_SHADOW_COLOR = new Color(50, 50, 50, 150);
    private static final Color BOX_COLOR = new Color(139, 69, 19);
    private static final Color DOG_AWAKE_COLOR = Color.RED;
    private static final Color DOG_ALERT_COLOR = Color.ORANGE;
    private static final Color DOG_DEFAULT_COLOR = Color.WHITE;
    private static final Color DOG_VISIBLE_COLOR = new Color(255, 0, 0, 100);

    private final HerdingHoundModel model;
    private final Image awakeImage;
    private final Image alertImage;
    private final Image asleepImage;

    public HerdingHoundView(final HerdingHoundModel model) {
        this.model = Objects.requireNonNull(model, "Model non può essere null");
        setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
        setBackground(BACKGROUND_COLOR);
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
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final int gridSize = model.getGrid();
        final int w = getWidth();
        final int h = getHeight();
        final int cellSize = Math.min(w, h) / gridSize;
        final int gridWidth = cellSize * gridSize;
        final int gridHeight = cellSize * gridSize;
        final int xOffset = (w - gridWidth) / 2;
        final int yOffset = (h - gridHeight) / 2;

        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, w, h);

        g.setColor(GRID_COLOR);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(xOffset, yOffset + i * cellSize, xOffset + gridWidth, yOffset + i * cellSize);
            g.drawLine(xOffset + i * cellSize, yOffset, xOffset + i * cellSize, yOffset + gridHeight);
        }

        g.setColor(VISIBLE_AREA_COLOR);
        for (Pair<Integer, Integer> pos : model.getDog().getVisibleArea()) {
            drawCell(g, pos, cellSize, xOffset, yOffset);
        }

        if (model.getDog().getState() == DogImpl.State.AWAKE) {
            g.setColor(DOG_VISIBLE_COLOR);
            for (Pair<Integer, Integer> pos : model.getVisible()) {
                drawCell(g, pos, cellSize, xOffset, yOffset);
            }
        }

        g.setColor(DOG_SHADOW_COLOR);
        for (Pair<Integer, Integer> shadow : model.getShadows()) {
            drawCell(g, shadow, cellSize, xOffset, yOffset);
        }

        g.setColor(BOX_COLOR);
        for (Pair<Integer, Integer> box : model.getBoxes()) {
            drawCell(g, box, cellSize, xOffset, yOffset);
        }

        final Pair<Integer, Integer> dogPos = model.getDog().getCoord();
        g.setColor(switch (model.getDog().getState()) {
            case AWAKE -> DOG_AWAKE_COLOR;
            case ALERT -> DOG_ALERT_COLOR;
            default -> DOG_DEFAULT_COLOR;
        });
        drawCell(g, dogPos, cellSize, xOffset, yOffset);

        if (model.getDog().getState() != DogImpl.State.ASLEEP) {
            final String symbol = model.getDog().getState() == DogImpl.State.AWAKE ? "!" : "?";
            g.setColor(Color.BLACK);
            final Font font = new Font("Arial", Font.BOLD, cellSize / 2);
            g.setFont(font);
            final FontMetrics fm = g.getFontMetrics();
            final int textWidth = fm.stringWidth(symbol);
            final int textHeight = fm.getHeight();
            final int centerX = xOffset + dogPos.getY() * cellSize + (cellSize - textWidth) / 2;
            final int centerY = yOffset + dogPos.getX() * cellSize - textHeight / 4;
            g.drawString(symbol, centerX, centerY);
        }

        g.setColor(Color.WHITE);
        drawCell(g, model.getGoose().getCoord(), cellSize, xOffset, yOffset);

        final long remMs = model.getRemainingTime();
        final long sec = remMs / 1000;
        final String text = String.format("Tempo %02d:%02d", sec / 60, sec % 60);

        g.setColor(Color.WHITE);
        final Font font = new Font("Arial", Font.BOLD, TIMER_FONT_SIZE);
        g.setFont(font);
        final FontMetrics fm = g.getFontMetrics();
        final int textWidth = fm.stringWidth(text);
        final int tx = w - textWidth - 10;
        final int ty = fm.getAscent() + 10;
        g.drawString(text, tx, ty);

        final Image stateImage = switch (model.getDog().getState()) {
            case AWAKE -> awakeImage;
            case ALERT -> alertImage;
            default -> asleepImage;
        };
        final int imgX = w - IMAGE_SIZE - 10;
        final int imgY = h - IMAGE_SIZE - 10;
        g.drawImage(stateImage, imgX, imgY, IMAGE_SIZE, IMAGE_SIZE, this);
    }

    private void drawCell(final Graphics g, final Pair<Integer, Integer> coord,
                          final int size, final int xOffset, final int yOffset) {
        final int x = xOffset + coord.getY() * size;
        final int y = yOffset + coord.getX() * size;
        g.fillRect(x, y, size, size);
    }

    /** Aggiorna la view. */
    public void updateView() {
        repaint();
    }

    /** Mostra un messaggio di fine gioco. */
    public void showGameOver() {
        final String message = switch (model.getGameState()) {
            case WON -> "Hai vinto!";
            case LOST -> "Hai perso!";
            default -> "";
        };
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(this, message, "Fine gioco",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
