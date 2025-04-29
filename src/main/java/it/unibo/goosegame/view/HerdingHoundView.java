package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.utilities.Pair;

import javax.swing.*;
import java.awt.*;

public class HerdingHoundView extends JPanel {
    private final HerdingHoundModel model;
    private static final int DEFAULT_SIZE = 600;

    public HerdingHoundView(HerdingHoundModel model) {
        this.model = model;
        setPreferredSize(new Dimension(DEFAULT_SIZE, DEFAULT_SIZE));
        setBackground(new Color(50, 50, 50));
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

            g.drawLine(xOffset,
                       yOffset + i * cellSize,
                       xOffset + gridWidth,
                       yOffset + i * cellSize);

            g.drawLine(xOffset + i * cellSize,
                       yOffset,
                       xOffset + i * cellSize,
                       yOffset + gridHeight);
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
        drawDogDirectionArrow(g, dogPos, model.getDog().getDirection(),
                              cellSize, xOffset, yOffset);

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
    }

    private void drawCell(Graphics g, Pair<Integer, Integer> coord,
                          int size, int xOffset, int yOffset) {
        int x = xOffset + coord.getY() * size;
        int y = yOffset + coord.getX() * size;
        g.fillRect(x, y, size, size);
    }

    private void drawDogDirectionArrow(Graphics g,
                                       Pair<Integer, Integer> dogPos,
                                       DogImpl.Direction dir,
                                       int size, int xOffset, int yOffset) {
        int centerX = xOffset + dogPos.getY() * size + size / 2;
        int centerY = yOffset + dogPos.getX() * size + size / 2;
        int arrowSize = size / 5;

        g.setColor(Color.BLACK);
        switch (dir) {
            case UP    -> g.drawLine(centerX, centerY, centerX, centerY - arrowSize);
            case DOWN  -> g.drawLine(centerX, centerY, centerX, centerY + arrowSize);
            case LEFT  -> g.drawLine(centerX, centerY, centerX - arrowSize, centerY);
            case RIGHT -> g.drawLine(centerX, centerY, centerX + arrowSize, centerY);
        }
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
