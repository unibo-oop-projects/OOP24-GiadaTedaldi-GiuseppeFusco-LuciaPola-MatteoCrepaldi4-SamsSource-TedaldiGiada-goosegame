package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;

public class DogLogicImpl implements Dog{
    
    private int x, y;
    private Direction direction;
    private int gridSize;

    public DogLogicImpl(int gridSize){
        this.x = gridSize/2;
        this.y = gridSize/2;
        this.direction = Direction.LEFT;
    }

    
    public void refreshDirection(GooseLogicImpl goose){
        int gx = goose.getX();
        int gy = goose.getY();
        
        if(gx==0 && y<gridSize-1){
            direction = Direction.LEFT;
        }else if(gy == gridSize-1 && x < gridSize -1){
            direction = Direction.DOWN;
        }else if (gx == gridSize -1 && y > 0){
            direction = Direction.LEFT;
        }
    }

    @Override
    public Direction getDirection(){
        return direction;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

}

