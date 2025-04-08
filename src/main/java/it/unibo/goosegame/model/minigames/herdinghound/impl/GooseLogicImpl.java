package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.utilities.Pair;

public class GooseLogicImpl implements Goose{
    
    private int x, y;

    public GooseLogicImpl(){
        this.x=0;
        this.y=0;
    }

    public Pair<Integer,Integer> getCoord(){
        return new Pair<>(this.x, this.y);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
}
