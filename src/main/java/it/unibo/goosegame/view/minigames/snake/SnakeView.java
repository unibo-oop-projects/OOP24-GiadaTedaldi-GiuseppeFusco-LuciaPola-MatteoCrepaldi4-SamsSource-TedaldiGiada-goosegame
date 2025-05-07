package it.unibo.goosegame.view.minigames.snake;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Position;

import java.awt.Color;
import java.awt.Graphics;

/**
 * The SnakeView class represents the view of the snake game.
 * It extends JPanel and is responsible for rendering the game state.
 */
public class SnakeView extends JPanel {
    private static final long serialVersionUID = 1L;
    private final SnakeModel model;
    private final JLabel score;

    /**
     * Constructor.
     * Initializes the SnakeView with a SnakeModel and sets the background color.
     */
    public SnakeView() {
        this.model = new SnakeModelImpl();
        setBackground(Color.BLACK);
        score = new JLabel("Score: " + model.getScore());
        score.setForeground(Color.WHITE);
        add(score);
    }

    /**
     * @inheritDoc
     * This method is responsible for painting the components of the snake game.
     * It draws the snake and the food on the panel.
     */
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
