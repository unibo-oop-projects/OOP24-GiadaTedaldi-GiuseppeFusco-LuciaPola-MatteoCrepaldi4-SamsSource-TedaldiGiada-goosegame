package it.unibo.goosegame.view.minigames.snake;
import javax.swing.*;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Position;

import java.awt.*;

public class SnakeView extends JPanel {
    private SnakeModel model;
    private JLabel score;

    public SnakeView(SnakeModel model) {
        this.model = model;
        setBackground(Color.BLACK);
        score = new JLabel("Score: " + model.getScore());
        score.setForeground(Color.WHITE);
        add(score);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellWidth = getWidth() / SnakeModelImpl.TABLE_WIDTH;
        int cellHeight = getHeight() / SnakeModelImpl.TABLE_HEIGHT;
        int cellSize = Math.min(cellWidth, cellHeight); 

        // Draw snake
        g.setColor(Color.GREEN);
        for (Position p : model.getSnakeBody()) {
            g.fillRect(p.x() * cellSize, p.y() * cellSize, cellSize, cellSize);
        }

        // Draw food
        g.setColor(Color.RED);
        Position food = model.getFood();
        g.fillRect(food.x() * cellSize, food.y() * cellSize, cellSize, cellSize);

        score.setText("Score: " + model.getScore());
    }
}
