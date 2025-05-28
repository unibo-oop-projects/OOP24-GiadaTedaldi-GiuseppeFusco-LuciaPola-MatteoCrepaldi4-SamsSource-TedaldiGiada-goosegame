package it.unibo.goosegame.view.minigames.snake;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Position;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

/**
 * The SnakeView class represents the view of the snake game.
 * It extends JPanel and is responsible for rendering the game state.
 */
public class SnakeView extends JFrame {
    private static final long serialVersionUID = 1L;
    private static final int INIT_HEIGHT = 400;
    private static final int INIT_WIDTH = 600;
    private final JLabel score;
    private final JPanel panelGame;

    /**
     * Constructor.
     * Initializes the SnakeView with a SnakeModel and sets the background color.
     * @param model the SnakeModel to be used for the view
     */
    public SnakeView(final SnakeModel model) {
        this.panelGame = new JPanel() {

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

                g.setColor(Color.GREEN);
                for (final Position p : model.getSnakeBody()) {
                    g.fillRect(p.x() * cellSize, p.y() * cellSize, cellSize, cellSize);
                }

                g.setColor(Color.RED);
                g.fillRect(model.getFood().x() * cellSize, model.getFood().y() * cellSize, cellSize, cellSize);

                score.setText("Score: " + model.getScore());
            }
        };
        panelGame.setBackground(Color.BLACK);
        score = new JLabel("Score: " + model.getScore());
        score.setForeground(Color.WHITE);
        panelGame.add(score);
        super.setTitle(model.getName());
        super.add(panelGame);
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.setTitle(model.getName());
        panelGame.setPreferredSize(new Dimension(INIT_WIDTH, INIT_HEIGHT));
        super.pack();
        super.setLocationRelativeTo(null);
        super.setVisible(true);
        super.setResizable(false); 
    }
}
