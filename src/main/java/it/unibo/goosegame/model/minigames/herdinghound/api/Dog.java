package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Pair;

public interface Dog{

    public enum Direction{
       UP, DOWN,LEFT, RIGHT
    }
    public enum State{
        ASLEEP, ALERT, AWAKE
    }
    
    Pair<Integer,Integer> getCoord();

    Direction getDirection();
}