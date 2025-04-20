package it.unibo.goosegame.view;

import it.unibo.goosegame.model.minigames.herdinghound.impl.DogImpl;
import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import it.unibo.goosegame.utilities.Pair;

import javax.swing.*;
import java.awt.*;

public class HerdingHoundView extends JPanel {
    private static final int CELL_SIZE = 50;
    private final HerdingHoundModel model;

    public HerdingHoundView(HerdingHoundModel model) {
        this.model = model;
        setPreferredSize(new Dimension(model.getGrid() * CELL_SIZE, model.getGrid() * CELL_SIZE));
        // Sfondo grigio scuro
        setBackground(new Color(50, 50, 50));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

    int gridSize = model.getGrid();

    // Sfondo
    g.setColor(Color.DARK_GRAY);
    g.fillRect(0, 0, getWidth(), getHeight());

    // Griglia
    g.setColor(Color.GRAY);
    for (int i = 0; i <= gridSize; i++) {
        g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, gridSize * CELL_SIZE);
        g.drawLine(0, i * CELL_SIZE, gridSize * CELL_SIZE, i * CELL_SIZE);
    }

    // Celle potenzialmente visibili dal cane (sempre grigio chiaro)
    g.setColor(Color.LIGHT_GRAY);
    for (Pair<Integer, Integer> pos : model.getDog().getVisibleArea()) {
        drawCell(g, pos, CELL_SIZE);
    }

    // Celle visibili dal cane (rosse trasparenti), solo se AWAKE
    if (model.getDog().getState() == DogImpl.State.AWAKE) {
        g.setColor(new Color(255, 0, 0, 100));
        for (Pair<Integer, Integer> pos : model.getVisible()) {
            drawCell(g, pos, CELL_SIZE);
        }
    }

    // Ombre (grigio scuro semitrasparente)
    g.setColor(new Color(50, 50, 50, 150));
    for (Pair<Integer, Integer> shadow : model.getShadows()) {
        drawCell(g, shadow, CELL_SIZE);
    }

    // Scatole (marrone)
    g.setColor(new Color(139, 69, 19));
    for (Pair<Integer, Integer> box : model.getBoxes()) {
        drawCell(g, box, CELL_SIZE);
    }

    // Cane (bianco)
    Pair<Integer, Integer> dogPos = model.getDog().getCoord();
        g.setColor(model.getDog().getState() == DogImpl.State.AWAKE ? Color.RED :
        model.getDog().getState() == DogImpl.State.ALERT ? Color.ORANGE : Color.WHITE);
        drawCell(g, dogPos, CELL_SIZE);
        drawDogDirectionArrow(g, dogPos, model.getDog().getDirection());

    // Oca (bianca)
    g.setColor(Color.WHITE);
    drawCell(g, model.getGoose().getCoord(), CELL_SIZE);

    long remMs = model.getRemainingTime();
    long sec = remMs / 1000;
    String text = String.format("%02d:%02d", sec / 60, sec % 60);
    
    g.setColor(Color.WHITE);
    Font font = new Font("Arial", Font.BOLD, 16);
    g.setFont(font);
    
    FontMetrics fm = g.getFontMetrics();
    int textWidth = fm.stringWidth(text);
    int x = getWidth() - textWidth - 10;
    int y = fm.getAscent() + 10;
    g.drawString(text, x, y);
}

    private void drawCell(Graphics g, Pair<Integer, Integer> coord, int size) {
        int x = coord.getY() * size;
        int y = coord.getX() * size;
        g.fillRect(x, y, size, size);
    }

    private void drawDogDirectionArrow(Graphics g, Pair<Integer, Integer> dogPos, DogImpl.Direction dir) {
        int centerX = dogPos.getY() * CELL_SIZE + CELL_SIZE / 2;
        int centerY = dogPos.getX() * CELL_SIZE + CELL_SIZE / 2;
        int arrowSize = 10;

        g.setColor(Color.BLACK);
        switch (dir) {
            case UP -> g.drawLine(centerX, centerY, centerX, centerY - arrowSize);
            case DOWN -> g.drawLine(centerX, centerY, centerX, centerY + arrowSize);
            case LEFT -> g.drawLine(centerX, centerY, centerX - arrowSize, centerY);
            case RIGHT -> g.drawLine(centerX, centerY, centerX + arrowSize, centerY);
        }
    }

    public void updateView() {
        repaint();
    }

    public void showGameOver() {
        String message = switch (model.getGameState()) {
            case WON -> "Hai vinto!";
            case LOST -> "Hai perso!";
            default -> "";
        };
        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(this, message, "Fine gioco", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
