package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.GooseLogic;

public class GooseLogicImpl implements GooseLogic{
    
    private int x, y;

    public GooseLogicImpl(int x, int y){
        this.x=x;
        this.y=y;
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
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
}
