package it.unibo.goosegame.model.minigames.herdinghound.impl;

import java.util.Random;

import it.unibo.goosegame.model.minigames.herdinghound.api.DogLogic;
import it.unibo.goosegame.utilities.Pair;

public class DogLogicImpl implements DogLogic{

    private int x, y;
    private boolean awake = false;
    private int direction;
    private Random random = new Random();

    public DogLogicImpl(int x, int y){
        this.x = x;
        this.y = y;
        this.direction = random.nextInt(4);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean isAwake() {
        return awake;
    }

    @Override
    public int getDirection() {
        return direction;
    }

    @Override
    public void wakeUp() {
        if(!awake){
            awake = true;
            direction = random.nextInt(4);
        }
    }

    @Override
    public boolean canSee(int ocaX, int ocaY, boolean[][] shadows) {
        if(!awake) return false;
        switch(direction){
            case 0:
                for (int i =y; i >=0; i--){
                    if(shadows[i][x]) break;
                    if(ocaX == x && ocaY == i) return true;
                } break;
            case 1: 
                for (int i = x; i<shadows[0].length; i++){
                    if (shadows[y][i]) break;
                    if (ocaX == i && ocaY == y) return true;
                    } break;
            case 2:
                for(int i = y; i<shadows.length; i++){
                    if (shadows[i][x]) break;
                    if (ocaX == x && ocaY == i) return true;
                } break;
            case 3:
                for(int i = x; i>=0; i--){
                    if(shadows[y][i]) break;
                    if(ocaX == i && ocaY == y) return true;
                } break;
            }
            return false;
    }
    
}
