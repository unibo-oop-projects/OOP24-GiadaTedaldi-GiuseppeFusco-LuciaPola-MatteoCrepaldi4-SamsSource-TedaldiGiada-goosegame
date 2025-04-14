package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Pair;

public interface Goose {

    public enum Direction{
        UP, DOWN,LEFT, RIGHT
     }
     
    public Pair<Integer,Integer> getCoord();

    void move(int dx, int dy);

}
