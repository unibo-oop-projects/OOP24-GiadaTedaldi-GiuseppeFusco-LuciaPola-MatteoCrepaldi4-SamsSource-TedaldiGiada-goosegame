package it.unibo.goosegame.view.minigames.snake;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.controller.minigames.snake.SnakeController;
import it.unibo.goosegame.view.general.impl.MinigameMenuImpl;

/**
 * The SnakeMenu class represents the menu for the snake game.
 * It extends the MinigameMenuImpl class and provides a specific implementation for the snake game.
 */
public class SnakeMenu extends MinigameMenuImpl {

    private static final long serialVersionUID = 1L;
    private transient SnakeController controller;

    /**
     * Constructor for the SnakeMenu class.
     */
    public SnakeMenu() {
        super(
            "/img/minigames/background/backgroundSnake.png", 
            "Snake Game", 
            "Welcome to Snake Game\n"
            + "Here's some instruction to play:\n"
            + "-Move the snake using < > ^ v buttons\n"
            + "-eat 15 apples and you win\n"
            + "-hit the walls and you lose"
        );
        initialize();
    }

    private void initialize() {
        getStartButton().addActionListener(e -> {
            controller = new SnakeController();
            super.setVisible(false);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        return controller == null ? GameState.NOT_STARTED : controller.getGameState();
    }
}
