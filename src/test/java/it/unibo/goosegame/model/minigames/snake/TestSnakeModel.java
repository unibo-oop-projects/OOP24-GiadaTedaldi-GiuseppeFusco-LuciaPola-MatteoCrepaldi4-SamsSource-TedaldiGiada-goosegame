package it.unibo.goosegame.model.minigames.snake;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

/**
 * Test class for SnakeModelImpl to validate core game functionalities.
 */
class SnakeModelImplTest {

    private static final int EXP_SCORE = 15;
    private SnakeModel model;

    @BeforeEach
    void setUp() {
        model = new SnakeModelImpl();
    }

    /**
     * Test to ensure the snake collides with the wall.
     */
    @Test
    void testSnakeCollisionWithWall() {
        model.resetGame();
        for (int i = 0; i < SnakeModelImpl.TABLE_WIDTH; i++) {
            model.move();
        }
        assertTrue(model.isOver());
    }

    @Test
    void testSnakeCollisionWithItself() {
        model.resetGame();
        final int temp1 = 5;
        final int temp2 = 6;
        final int temp3 = 4;
        final List<Position> body = List.of(
            new Position(temp1, temp1), 
            new Position(temp1, temp2),
            new Position(temp2, temp2),
            new Position(temp2, temp1),
            new Position(temp2, temp3),
            new Position(temp1, temp3)
        );
        model.setSnakeBody(body);
        model.changeDirection(Direction.LEFT);
        model.move(); 
        model.changeDirection(Direction.DOWN); 
        model.move();
        model.changeDirection(Direction.RIGHT); 
        model.move();
        assertTrue(model.isOver(), "Expected collision with self to end game");
    }

    /**
     * Test to ensure the snake correctly eats food and increments the score.
     */
    @Test
    void testSnakeEatsFood() {
        model.resetGame();
        final Position head = model.getSnakeBody().get(0);
        final Position food = new Position(head.x() + 1, head.y());
        model.changeDirection(Direction.RIGHT);
        model.setFood(food);
        final int oldScore = model.getScore();
        model.move();
        assertEquals(oldScore + 1, model.getScore());
        assertEquals(2, model.getSnakeBody().size());
    }

    /**
     * Test to verify that the snake wins after reaching the score of WIN_SCORE.
     */
    @Test
    void testSnakeWinsGame() {
        model.resetGame();
        final int score = 14;
        model.setScore(score);
        final Position head = model.getSnakeBody().get(0);
        final Position food = new Position(head.x() + 1, head.y());
        model.changeDirection(Direction.RIGHT);
        model.setFood(food);
        model.move();
        assertEquals(EXP_SCORE, model.getScore());
        assertTrue(model.checkWin());
        assertEquals(1, model.getResult());
    }

    /**
     * Test to verify that the game is reset properly.
     */
    @Test
    void testGameReset() {
        model.move();
        model.move();
        model.resetGame();
        assertEquals(0, model.getScore());
        assertEquals(1, model.getSnakeBody().size());
        assertFalse(model.isOver());
    }
}
