package it.unibo.goosegame.model.minigames.herdinghound.api;

import java.util.*;

import it.unibo.goosegame.utilities.Position;

/**
 * Rappresenta una scatola/ostacolo nel minigioco Herding Hound.
 * Gestisce la posizione e la generazione delle scatole.
 */
public interface Box {

    List<Position> getBoxes();



    void generateBoxes();

}
