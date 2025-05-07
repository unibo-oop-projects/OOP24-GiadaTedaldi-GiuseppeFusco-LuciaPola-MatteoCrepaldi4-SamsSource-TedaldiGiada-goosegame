package it.unibo.goosegame.model.minigames.snake.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

/**
 * Implementation of the {@link SnakeModel} interface.
 * This class represents the logic of the "Snake" mini-game,
 * managing the snake's body, food, direction, and game status.
 */
public class SnakeModelImpl implements SnakeModel {
    /**
     * Width of the game table.
     */
    public static final int TABLE_WIDTH = 30;
    /**
     * Height of the game table.
     */
    public static final int TABLE_HEIGHT = 20;
    /**
     * The score needed to win the game.
     */
    private static final int WIN_SCORE = 15;
    private List<Position> snakeBody;
    private Position food;
    private Direction direction;
    private boolean isGameOver;
    private int score;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Snake Game";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        return this.isGameOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResult() {
        return checkWin() ? 1 : -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int move() {
        if (isGameOver) {
            return 0;
        }
        final Position head = snakeBody.get(0);
        final Position newHead = getNextPosition(head);
        if (checkCollision(newHead)) {
            isGameOver = true;
            return 1;
        }
        snakeBody.add(0, newHead);
        if (newHead.equals(food)) {
            score++;
            generateFood();
        } else {
            snakeBody.remove(snakeBody.size() - 1);
        }
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeDirection(final Direction newDirection) {
        if ((direction == Direction.UP && newDirection != Direction.DOWN)
        || (direction == Direction.DOWN && newDirection != Direction.UP)
        || (direction == Direction.LEFT && newDirection != Direction.RIGHT)
        || (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
            direction = newDirection;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkWin() {
        return this.score == WIN_SCORE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Position> getSnakeBody() {
        return this.snakeBody;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getFood() {
        return this.food;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
        this.snakeBody = new ArrayList<>();
        this.snakeBody.add(new Position(TABLE_WIDTH / 2, TABLE_HEIGHT / 2));
        this.direction = Direction.RIGHT;
        this.isGameOver = false;
        this.score = 0;
        generateFood();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setFood(final Position p) {
        this.food = p;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSnakeBody(final List<Position> body) {
        this.snakeBody = body;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScore(final int score) {
        this.score = score;
    }

    /**
     * Calculates the next position of the snake's head based on the current direction.
     *
     * @param head The current position of the snake's head.
     * @return The new position of the snake's head.
     */
    private Position getNextPosition(final Position head) {
        int x = head.x();
        int y = head.y();
        switch (direction) {
            case UP: y--; break;
            case DOWN: y++; break;
            case LEFT: x--; break;
            case RIGHT: x++; break;
        }
        return new Position(x, y);
    }

    /**
     * Checks if the snake's new head position collides with the wall or itself.
     *
     * @param newHead The new position of the snake's head.
     * @return {@code true} if there is a collision, {@code false} otherwise.
     */
    private boolean checkCollision(final Position newHead) {
        if (newHead.x() < 0 || newHead.x() >= TABLE_WIDTH || newHead.y() < 0 || newHead.y() >= TABLE_HEIGHT) {
            return true;
        }
        for (final Position segment : snakeBody) {
            if (segment.equals(newHead)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Generates a new food position on the board that does not overlap with the snake's body.
     */
    private void generateFood() {
        final Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(TABLE_WIDTH);
            y = rand.nextInt(TABLE_HEIGHT);
            food = new Position(x, y);
        } while (snakeBody.contains(food));
    }
}
