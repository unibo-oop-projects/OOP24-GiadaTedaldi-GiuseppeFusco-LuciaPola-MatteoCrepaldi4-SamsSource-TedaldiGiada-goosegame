package it.unibo.goosegame.model.minigames.herdinghound.api;

import java.util.*;

import it.unibo.goosegame.utilities.Pair;

public interface Boxgen {

    List<Pair<Integer,Integer>> getBoxes();

    void generateBoxes();

}
