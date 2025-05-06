package it.unibo.goosegame.model.minigames.snake;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.model.minigames.snake.impl.SnakeModelImpl;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Test class for SnakeModelImpl to validate core game functionalities.
 */
class SnakeModelImplTest {

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
        List<Position> body = List.of(
            new Position(5, 5), 
            new Position(5, 6),
            new Position(6, 6),
            new Position(6, 5),
            new Position(6, 4),
            new Position(5, 4)
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
    
        Position head = model.getSnakeBody().get(0);
        Position food = new Position(head.x() + 1, head.y());
    
        model.changeDirection(Direction.RIGHT);
        model.setFood(food);
    
        int oldScore = model.getScore();
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
        model.setScore(14);
        Position head = model.getSnakeBody().get(0);
        Position food = new Position(head.x() + 1, head.y());
        model.changeDirection(Direction.RIGHT);
        model.setFood(food);
    
        model.move();
    
        assertEquals(15, model.getScore());
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
