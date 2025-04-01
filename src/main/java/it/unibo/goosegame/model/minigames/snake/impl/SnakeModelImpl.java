package it.unibo.goosegame.model.minigames.snake.impl;

import java.util.Random;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.utilities.Direction;

public class SnakeModelImpl implements SnakeModel {

    int bodyParts;
    Random random;
    Direction dir;

    public SnakeModelImpl(){
        random = new Random();
        changeDirection(Direction.UP);
    }

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
    public void changeDirection(Direction newDirection) {
        switch(newDirection){
            case UP: dir = Direction.UP; break;
            case DOWN: dir = Direction.DOWN; break;
            case LEFT: dir = Direction.LEFT; break;
            case RIGHT: dir = Direction.RIGHT; break;
        }
    }

    @Override
    public boolean checkFoodEaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkFoodEaten'");
    }

}
