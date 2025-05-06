package it.unibo.goosegame.model.minigames.snake.api;
import java.util.List;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

/**
 * Interface representing the model for the Snake minigame.
 * It provides methods to manage the game state, control the snake's movement,
 * and check game conditions such as winning or scoring.
 */
public interface SnakeModel extends MinigamesModel {

    /**
     * Resets the game to its initial state.
     */
    void resetGame();

    /**
     * Moves the snake in the current direction and updates the game state.
     * 
     * @return the result of the move, typically indicating the game's status.
     */
    int move();

    /**
     * Changes the direction of the snake's movement.
     * 
     * @param newDirection the new direction for the snake to move.
     */
    void changeDirection(Direction newDirection);

    /**
     * Checks if the player has won the game.
     * 
     * @return {@code true} if the player has won, {@code false} otherwise.
     */
    boolean checkWin();

    /**
     * Retrieves the current positions of the snake's body segments.
     * 
     * @return a list of positions representing the snake's body.
     */
    List<Position> getSnakeBody();

    /**
     * Retrieves the position of the food item in the game.
     * 
     * @return the position of the food.
     */
    Position getFood();

    /**
     * Retrieves the current score of the game.
     * 
     * @return the current score.
     */
    int getScore();
}