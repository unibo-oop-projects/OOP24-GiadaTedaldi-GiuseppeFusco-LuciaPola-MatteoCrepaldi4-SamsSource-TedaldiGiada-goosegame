package it.unibo.goosegame.view.minigames.snake;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
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
    private final JLabel score;
    private final transient SnakeModel model;

    /**
     * Constructor.
     * Initializes the SnakeView with a SnakeModel and sets the background color.
     * @param model the SnakeModel to be used for the view
     */
    @SuppressFBWarnings(
        value = "EI2",
        justification = "Reference to mutable model is intentional in this class: need it to update the view."
    )
    public SnakeView(final SnakeModel model) {
        this.model = model;
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
        for (final Position p : model.getSnakeBody()) {
            g.fillRect(p.x() * cellSize, p.y() * cellSize, cellSize, cellSize);
        }

        // Draw food
        g.setColor(Color.RED);
        g.fillRect(model.getFood().x() * cellSize, model.getFood().y() * cellSize, cellSize, cellSize);

        score.setText("Score: " + model.getScore());
    }
}
