package it.unibo.goosegame.model.minigames.snake.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

public class SnakeModelImpl implements SnakeModel {

    private static final int initSnakeLength = 3;
    private static List<Position> snakeBody;
    private static Random random;
    private static Direction currentDir;

    public SnakeModelImpl(){
        this.snakeBody = new LinkedList<>();
        for (int i = 0; i < initSnakeLength; i++) {
            snakeBody.add(new Position(5 - i, 5));
        }
        this.random = new Random();
        this.currentDir = Direction.LEFT;
    }

    @Override
    public void move() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkCollision'");
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
            case UP: currentDir = Direction.UP; break;
            case DOWN: currentDir = Direction.DOWN; break;
            case LEFT: currentDir = Direction.LEFT; break;
            case RIGHT: currentDir = Direction.RIGHT; break;
        }
    }

    @Override
    public boolean checkFoodEaten() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkFoodEaten'");
    }

}
