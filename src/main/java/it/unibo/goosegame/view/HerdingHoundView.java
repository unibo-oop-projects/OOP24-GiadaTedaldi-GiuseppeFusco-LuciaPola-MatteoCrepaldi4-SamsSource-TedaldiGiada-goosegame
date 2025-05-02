package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.utilities.Pair;

import javax.swing.*;
import java.awt.*;

public class HerdingHoundView extends JPanel {
    private final HerdingHoundModel model;
    private static final int DEFAULT_SIZE = 600;

    private final Image awakeImage;
    private final Image alertImage;
    private final Image asleepImage;

    public HerdingHoundView(HerdingHoundModel model) {
        this.model = model;
        setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
        setBackground(new Color(50, 50, 50));

        this.awakeImage = new ImageIcon("C:\\Users\\HEW\\OneDrive\\Desktop\\ProgrammazioneOgetti\\ProgOOP\\OOP24-goosegame\\src\\main\\resources\\img\\dog_awake.png").getImage();
        this.alertImage = new ImageIcon("C:\\\\Users\\\\HEW\\\\OneDrive\\\\Desktop\\\\ProgrammazioneOgetti\\\\ProgOOP\\\\OOP24-goosegame\\\\src\\\\main\\\\resources\\\\img\\\\dog_alert.png").getImage();
        this.asleepImage = new ImageIcon("C:\\Users\\HEW\\OneDrive\\Desktop\\ProgrammazioneOgetti\\ProgOOP\\OOP24-goosegame\\src\\main\\resources\\img\\dog_asleep.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int gridSize = model.getGrid();
        int w = getWidth();
        int h = getHeight();

        int cellSize = Math.min(w, h) / gridSize;
        int gridWidth  = cellSize * gridSize;
        int gridHeight = cellSize * gridSize;

        int xOffset = (w - gridWidth) / 2;
        int yOffset = (h - gridHeight) / 2;

        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, w, h);

        g.setColor(Color.GRAY);
        for (int i = 0; i <= gridSize; i++) {
            g.drawLine(xOffset, yOffset + i * cellSize, xOffset + gridWidth, yOffset + i * cellSize);
            g.drawLine(xOffset + i * cellSize, yOffset, xOffset + i * cellSize, yOffset + gridHeight);
        }

        g.setColor(Color.LIGHT_GRAY);
        for (Pair<Integer, Integer> pos : model.getDog().getVisibleArea()) {
            drawCell(g, pos, cellSize, xOffset, yOffset);
        }

        if (model.getDog().getState() == DogImpl.State.AWAKE) {
            g.setColor(new Color(255, 0, 0, 100));
            for (Pair<Integer, Integer> pos : model.getVisible()) {
                drawCell(g, pos, cellSize, xOffset, yOffset);
            }
        }

        g.setColor(new Color(50, 50, 50, 150));
        for (Pair<Integer, Integer> shadow : model.getShadows()) {
            drawCell(g, shadow, cellSize, xOffset, yOffset);
        }

        g.setColor(new Color(139, 69, 19));
        for (Pair<Integer, Integer> box : model.getBoxes()) {
            drawCell(g, box, cellSize, xOffset, yOffset);
        }

        Pair<Integer, Integer> dogPos = model.getDog().getCoord();
        g.setColor(switch (model.getDog().getState()) {
            case AWAKE  -> Color.RED;
            case ALERT  -> Color.ORANGE;
            default     -> Color.WHITE;
        });
        drawCell(g, dogPos, cellSize, xOffset, yOffset);

        // DISEGNA PUNTO INTERROGATIVO/ESCLAMATIVO SOPRA IL CANE
        if (model.getDog().getState() != DogImpl.State.ASLEEP) {
            String symbol = model.getDog().getState() == DogImpl.State.AWAKE ? "!" : "?";
            g.setColor(Color.BLACK);
            Font font = new Font("Arial", Font.BOLD, cellSize / 2);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(symbol);
            int textHeight = fm.getHeight();

            int centerX = xOffset + dogPos.getY() * cellSize + (cellSize - textWidth) / 2;
            int centerY = yOffset + dogPos.getX() * cellSize - textHeight / 4;
            g.drawString(symbol, centerX, centerY);
        }

        g.setColor(Color.WHITE);
        drawCell(g, model.getGoose().getCoord(), cellSize, xOffset, yOffset);

        long remMs = model.getRemainingTime();
        long sec   = remMs / 1000;
        String text = String.format("Tempo %02d:%02d", sec / 60, sec % 60);

        g.setColor(Color.WHITE);
        Font font = new Font("Arial", Font.BOLD, 16);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int tx = w - textWidth - 10;
        int ty = fm.getAscent() + 10;
        g.drawString(text, tx, ty);

        Image stateImage = switch (model.getDog().getState()) {
            case AWAKE -> awakeImage;
            case ALERT -> alertImage;
            default -> asleepImage;
        };

        int imageSize = 200;
        int imgX = w - imageSize - 10;
        int imgY = h - imageSize - 10;
        g.drawImage(stateImage, imgX, imgY, imageSize, imageSize, this);
    }

    private void drawCell(Graphics g, Pair<Integer, Integer> coord,
                          int size, int xOffset, int yOffset) {
        int x = xOffset + coord.getY() * size;
        int y = yOffset + coord.getX() * size;
        g.fillRect(x, y, size, size);
    }

    public void updateView() {
        repaint();
    }

    public void showGameOver() {
        String message = switch (model.getGameState()) {
            case WON  -> "Hai vinto!";
            case LOST -> "Hai perso!";
            default   -> "";
        };
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(this, message, "Fine gioco",
                                          JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

