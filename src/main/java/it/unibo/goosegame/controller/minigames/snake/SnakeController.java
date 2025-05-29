package it.unibo.goosegame.controller.minigames.snake;

import javax.swing.Timer;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.view.minigames.snake.SnakeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Controller for the Snake game.
 * It handles user input and updates the game state.
 */
public class SnakeController {
    private final SnakeModel model;
    private final SnakeView view;
    private final Timer timer;

    /**
     * Constructor for SnakeController.
     * Initializes the model and view for the Snake game.
     */
    public SnakeController() {
        model = new SnakeModelImpl();
        view = new SnakeView(model);

        view.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> model.changeDirection(Direction.UP);
                    case KeyEvent.VK_DOWN -> model.changeDirection(Direction.DOWN);
                    case KeyEvent.VK_LEFT -> model.changeDirection(Direction.LEFT);
                    case KeyEvent.VK_RIGHT -> model.changeDirection(Direction.RIGHT);
                    default -> { 

                    }
                }
            }
        });

        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.move();
                view.repaint();
                if (model.isOver() || model.checkWin()) {
                    timer.stop();
                    view.showOverMessage(model.getGameState() == GameState.WON);
                }
            }
        });
        timer.start();
    }
}
