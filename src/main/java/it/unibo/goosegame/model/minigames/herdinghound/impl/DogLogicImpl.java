package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;
import it.unibo.goosegame.utilities.Pair;

public class DogLogicImpl implements Dog{
    
    private int x, y;
    private Direction direction;
    private int gridSize;
    private State state;
    public DogLogicImpl(int gridSize){
        this.x = gridSize/2;
        this.y = gridSize/2;
        this.direction = Direction.LEFT;
        this.state = State.ASLEEP;
    }

    
    public void refreshDirection(GooseLogicImpl goose){
        int gx = goose.getCoord().getX();
        int gy = goose.getCoord().getY();
        
        if(gx==0 && y<gridSize-1){
            direction = Direction.LEFT;
        }else if(gy == gridSize-1 && x < gridSize -1){
            direction = Direction.DOWN;
        }else if (gx == gridSize -1 && y > 0){
            direction = Direction.LEFT;
        }
    }

    public void refreshState(){
        if(state.equals(State.ASLEEP)){
            this.state=State.ALERT;
        }else if(state.equals(State.ALERT)){
            this.state=State.AWAKE;
        }else if(state.equals(State.AWAKE)){
            this.state=State.ASLEEP;
        }
    }

    public State getState(){
        return this.state;
    }

    @Override
    public Direction getDirection(){
        return direction;
    }

    @Override
   public Pair<Integer,Integer> getCoord(){
        return new Pair<>(this.x, this.y);
    }

}

