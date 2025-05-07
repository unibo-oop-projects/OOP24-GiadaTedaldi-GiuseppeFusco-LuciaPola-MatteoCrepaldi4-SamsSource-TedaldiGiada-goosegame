package it.unibo.goosegame.view.minigames.snake;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Position;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

/**
 * The SnakeView class represents the view of the snake game.
 * It extends JPanel and is responsible for rendering the game state.
 */
public class SnakeView extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JLabel score;
    private final List<Position> snakeBody;
    private final Position food;
    private final int scorePoint;

    /**
     * Constructor.
     * Initializes the SnakeView with a SnakeModel and sets the background color.
     * @param model the SnakeModel to be used for the view
     */
    public SnakeView(final SnakeModel model) {
        this.snakeBody = List.copyOf(model.getSnakeBody());
        this.food = model.getFood();
        this.scorePoint = model.getScore();
        super.setBackground(Color.BLACK);
        score = new JLabel("Score: " + model.getScore());
        score.setForeground(Color.WHITE);
        super.add(score);
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
        for (final Position p : snakeBody) {
            g.fillRect(p.x() * cellSize, p.y() * cellSize, cellSize, cellSize);
        }

        // Draw food
        g.setColor(Color.RED);
        g.fillRect(food.x() * cellSize, food.y() * cellSize, cellSize, cellSize);

        score.setText("Score: " + scorePoint);
    }
}
