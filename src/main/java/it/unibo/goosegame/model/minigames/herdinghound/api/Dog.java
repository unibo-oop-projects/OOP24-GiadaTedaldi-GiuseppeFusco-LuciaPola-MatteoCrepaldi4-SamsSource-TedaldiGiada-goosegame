package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Position;

/**
 * Rappresenta il cane nel minigioco Herding Hound.
 * Gestisce la posizione, la direzione e lo stato.
 */
public interface Dog{

    /**
     * Direzioni possibili del cane.
     */
    public enum Direction {
       UP, DOWN,LEFT, RIGHT
    }
    /**
     * Stati possibili del cane.
     */
    public enum State {
        ASLEEP, ALERT, AWAKE
    }
    
    /**
     * Restituisce la posizione corrente del cane.
     * @return la posizione attuale
     */
    Position getCoord();

    /**
     * Restituisce la direzione attuale del cane.
     * @return la direzione
     */
    Direction getDirection();
}