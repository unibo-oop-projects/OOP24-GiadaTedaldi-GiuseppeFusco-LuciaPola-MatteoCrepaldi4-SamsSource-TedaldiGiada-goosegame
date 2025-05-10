package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Position;

public interface Goose {

    public enum Direction{
        UP, DOWN,LEFT, RIGHT
     }
     
    public Position getCoord();

    void move(int dx, int dy);

}
