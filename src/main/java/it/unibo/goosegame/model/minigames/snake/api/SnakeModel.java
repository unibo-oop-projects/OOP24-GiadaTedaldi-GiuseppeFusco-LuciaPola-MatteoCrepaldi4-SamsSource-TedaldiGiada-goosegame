package it.unibo.goosegame.model.minigames.snake.api;
import java.util.List;

import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

public interface SnakeModel {
    void resetGame();
    int move();
    void changeDirection(Direction newDirection);
    boolean checkWin();
    List<Position> getSnakeBody();
    Position getFood();
    int getScore();
}