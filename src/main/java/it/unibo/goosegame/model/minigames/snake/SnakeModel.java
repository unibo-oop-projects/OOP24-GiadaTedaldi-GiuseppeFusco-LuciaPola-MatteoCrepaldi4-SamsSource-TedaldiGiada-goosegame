package it.unibo.goosegame.model.minigames.snake;

public interface SnakeModel {
    /**
     * Moves the snake in the current direction
     */
    void move();

    /**
     * Checks if the snake hits himself or the boundaries
     */
    void checkCollision();

    /**
     * Places food at a random location
     */
    void generateFood();

    /**
     * Updates the direction of the snake
     * @param newDirection is the new direction of the snake
     */
    void changeDirection(Character newDirection);

    /**
     * Detects if the snake ate the food
     * @return whether the snake ates food or not
     */
    boolean checkFoodEaten();
}
