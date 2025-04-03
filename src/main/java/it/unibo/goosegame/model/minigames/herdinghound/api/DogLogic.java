package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;
import java.util.Random;

public interface DogLogic{

    public int getX();

    public int getY();

    public boolean isAwake();

    public int getDirection();

    public void wakeUp();

    public boolean canSee(int ocaX, int ocaY, boolean[][] shadows);
}