package it.unibo.goosegame.view.minigames.snake;
import javax.swing.*;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Position;

import java.awt.*;

public class SnakeView extends JPanel {
    private final SnakeModel model;
    private final JLabel score;

    public SnakeView() {
        this.model = new SnakeModelImpl();
        setBackground(Color.BLACK);
        score = new JLabel("Score: " + model.getScore());
        score.setForeground(Color.WHITE);
        add(score);
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        final int cellWidth = getWidth() / SnakeModelImpl.TABLE_WIDTH;
        final int cellHeight = getHeight() / SnakeModelImpl.TABLE_HEIGHT;
        final int cellSize = Math.min(cellWidth, cellHeight); 

        // Draw snake
        g.setColor(Color.GREEN);
        for (final Position p : model.getSnakeBody()) {
            g.fillRect(p.x() * cellSize, p.y() * cellSize, cellSize, cellSize);
        }

        // Draw food
        g.setColor(Color.RED);
        final Position food = model.getFood();
        g.fillRect(food.x() * cellSize, food.y() * cellSize, cellSize, cellSize);

        score.setText("Score: " + model.getScore());
    }
}
