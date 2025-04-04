package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.model.general.MinigamesModel;
import it.unibo.goosegame.utilities.Position;
import java.util.Random;

public interface Dog{

    int getX();

    int getY();

    boolean isAwake();

    int getDirection();

    void wakeUp();

    boolean canSee(int ocaX, int ocaY, boolean[][] shadows);
}