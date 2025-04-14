package it.unibo.goosegame.model.minigames.herdinghound.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;
import it.unibo.goosegame.utilities.Pair;

public class DogImpl implements Dog{
    
    private int x, y;
    private Direction direction;
    private int gridSize;
    private State state;
    private List<Pair<Integer, Integer>> visibleArea;

    public DogImpl(int gridSize){
        this.x = gridSize/2;
        this.y = gridSize/2;
        this.direction = Direction.LEFT;
        this.state = State.ASLEEP;
        this.visibleArea = new ArrayList<>();
    }

    
    public void refreshDirection(GooseImpl goose){
        int gx = goose.getCoord().getX();
        int gy = goose.getCoord().getY();
        
        if(gx==0 && y<gridSize-1){
            direction = Direction.LEFT;
        }else if(gy == gridSize-1 && x < gridSize -1){
            direction = Direction.DOWN;
        }else if (gx == gridSize -1 && y > 0){
            direction = Direction.RIGHT;
        }else{
            direction = Direction.UP;
        }
        updateVisibleArea();
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

    private void updateVisibleArea() {
        this.visibleArea.clear();

        if(this.state.equals(State.AWAKE)){
        switch (this.direction) {
            case UP:
                for (int dy = 1; y - dy >= 0; dy++) {
                    int startX = x - dy;
                    int endX = x + dy;
                    for (int dx = startX; dx <= endX; dx++) {
                        if (dx >= 0 && dx < gridSize) {
                            visibleArea.add(new Pair<>(dx, y - dy));
                        }
                    }
                }
                break;

            case DOWN:
                for (int dy = 1; y + dy < gridSize; dy++) {
                    int startX = x - dy;
                    int endX = x + dy;
                    for (int dx = startX; dx <= endX; dx++) {
                        if (dx >= 0 && dx < gridSize) {
                            visibleArea.add(new Pair<>(dx, y + dy));
                        }
                    }
                }
                break;

            case LEFT:
                for (int dx = 1; x - dx >= 0; dx++) {
                    int startY = y - dx;
                    int endY = y + dx;
                    for (int dy = startY; dy <= endY; dy++) {
                        if (dy >= 0 && dy < gridSize) {
                            visibleArea.add(new Pair<>(x - dx, dy));
                        }
                    }
                }
                break;

            case RIGHT:
                for (int dx = 1; x + dx < gridSize; dx++) {
                    int startY = y - dx;
                    int endY = y + dx;
                    for (int dy = startY; dy <= endY; dy++) {
                        if (dy >= 0 && dy < gridSize) {
                            visibleArea.add(new Pair<>(x + dx, dy));
                        }
                    }
                }
                break;
            }
        }
    }

    public List<Pair<Integer, Integer>> getVisibleArea() {
        return new ArrayList<>(visibleArea);
    }

    public void reset() {
        this.x = gridSize / 2;
        this.y = gridSize / 2;
        this.direction = Direction.LEFT;
        this.state = State.ASLEEP;
        this.visibleArea.clear();
    }

}

