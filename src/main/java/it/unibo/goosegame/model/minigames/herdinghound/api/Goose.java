package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Position;

/**
 * Rappresenta l'oca nel minigioco Herding Hound.
 * Gestisce la posizione e il movimento.
 */
public interface Goose {

    /**
     * Direzioni possibili di movimento dell'oca.
     */
    public enum Direction {
        UP, DOWN,LEFT, RIGHT
     }
     
    /**
     * Restituisce la posizione corrente dell'oca.
     * @return la posizione attuale
     */
    public Position getCoord();

    /**
     * Muove l'oca di una certa quantità nelle due direzioni.
     * @param dx spostamento orizzontale
     * @param dy spostamento verticale
     */
    void move(int dx, int dy);

}
