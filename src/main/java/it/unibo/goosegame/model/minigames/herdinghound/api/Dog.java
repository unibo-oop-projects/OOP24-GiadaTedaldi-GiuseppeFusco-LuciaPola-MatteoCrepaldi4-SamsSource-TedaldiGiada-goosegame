package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.model.minigames.herdinghound.impl.GooseLogicImpl;
import it.unibo.goosegame.utilities.Position;
import java.util.Random;

public interface Dog{

    public enum Direction{
        DOWN,LEFT, RIGHT
    }
    public enum State{
        ASLEEP, ALERT, AWAKE
    }
    
    int getX();

    int getY();

    void refreshDirection(GooseLogicImpl goose);

    Direction getDirection();
}