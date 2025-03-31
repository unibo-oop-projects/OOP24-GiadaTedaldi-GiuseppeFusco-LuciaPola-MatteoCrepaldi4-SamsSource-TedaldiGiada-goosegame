package it.unibo.goosegame.model.general;

import it.unibo.goosegame.utilities.Pair;

public interface MinigamesModel {

    void resetGame();

    Pair<String,Integer> getResult();

    String getName();

    boolean isOver();

}
