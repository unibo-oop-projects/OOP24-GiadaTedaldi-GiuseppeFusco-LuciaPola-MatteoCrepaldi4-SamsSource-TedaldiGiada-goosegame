package it.unibo.goosegame.model.minigames.snake.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.goosegame.model.minigames.snake.api.SnakeModel;
import it.unibo.goosegame.utilities.Direction;
import it.unibo.goosegame.utilities.Position;

public class SnakeModelImpl implements SnakeModel {
    private static final int WIN_SCORE = 15;
    public static final int TABLE_WIDTH = 30; 
    public static final int TABLE_HEIGHT = 20;
    
    private List<Position> snakeBody;
    private Position food;
    private Direction direction;
    private boolean isGameOver;
    private int score;
    
    public SnakeModelImpl() {
        resetGame();
    }
    
    @Override
    public String getName() {
        return "Snake Game";
    }

    @Override
    public boolean isOver() {
        return this.isGameOver;
    }

    @Override
    public int getResult() {
        return checkWin() ? 1 : -1;
    }

    @Override
    public int move() {
        if (isGameOver) {
            return 0;
        }
        
        Position head = snakeBody.get(0);
        Position newHead = getNextPosition(head);
        
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

    @Override
    public void changeDirection(Direction newDirection) {
        if ((direction == Direction.UP && newDirection != Direction.DOWN) ||
        (direction == Direction.DOWN && newDirection != Direction.UP) ||
        (direction == Direction.LEFT && newDirection != Direction.RIGHT) ||
        (direction == Direction.RIGHT && newDirection != Direction.LEFT)) {
        direction = newDirection;
    }
    }

    @Override
    public boolean checkWin() {
        return this.score == WIN_SCORE;
    }

    @Override
    public List<Position> getSnakeBody() {
        return this.snakeBody;
    }

    @Override
    public Position getFood() {
        return this.food;
    }

    @Override
    public int getScore() {
        return this.score;
    }

    @Override
    public void resetGame() {
        this.snakeBody = new ArrayList<>();
        this.snakeBody.add(new Position(TABLE_WIDTH / 2, TABLE_HEIGHT / 2));
        this.direction = Direction.RIGHT;
        this.isGameOver = false;
        this.score = 0;
        generateFood();
    }

    private Position getNextPosition(Position head) {
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

    private boolean checkCollision(Position newHead) {
        if(newHead.x() < 0 || newHead.x() >= TABLE_WIDTH || newHead.y() < 0 || newHead.y() >= TABLE_HEIGHT) {
            return true;
        }
        for (Position segment : snakeBody) {
            if (segment.equals(newHead)) {
                return true;
            }
        }
        return false;
    }
    
    private void generateFood() {
        Random rand = new Random();
        int x, y;
        do {
            x = rand.nextInt(TABLE_WIDTH);
            y = rand.nextInt(TABLE_HEIGHT);
            food = new Position(x, y);
        } while (snakeBody.contains(food));
    }
}