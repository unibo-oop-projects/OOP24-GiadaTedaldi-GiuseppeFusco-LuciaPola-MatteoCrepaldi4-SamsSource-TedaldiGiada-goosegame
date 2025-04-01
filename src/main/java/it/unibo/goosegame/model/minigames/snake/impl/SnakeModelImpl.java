package it.unibo.goosegame.model.minigames.snake.impl;

import java.util.List;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Pair;
import it.unibo.goosegame.utilities.Position;

public class SnakeModelImpl implements SnakeModel, MinigamesModel {

    List<Position> snakeBody;
    Position food;
    Direction direction;
    boolean isGameOver;

    @Override
    public void move() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'move'");
    }

    @Override
    public void checkCollision() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkCollision'");
    }

    @Override
    public void generateFood() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generateFood'");
    }

    @Override
    public void changeDirection(Character newDirection) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeDirection'");
    }

    @Override
    public boolean checkFoodEaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkFoodEaten'");
    }

    @Override
    public void resetGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetGame'");
    }

    @Override
    public Pair<String, Integer> getResult() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResult'");
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getName'");
    }

    @Override
    public boolean isOver() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isOver'");
    }

}
