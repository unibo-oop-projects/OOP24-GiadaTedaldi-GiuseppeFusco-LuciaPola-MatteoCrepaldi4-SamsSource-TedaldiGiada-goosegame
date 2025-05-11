package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Position;

/**
 * Represents the goose in the Herding Hound minigame.
 * Manages the position and movement.
 */
public interface Goose {

    /**
     * Possible movement directions for the goose.
     */
    public enum Direction {
        UP, DOWN,LEFT, RIGHT
     }
     
    /**
     * Returns the current position of the goose.
     * @return the current position
     */
    public Position getCoord();

    /**
     * Moves the goose by a certain amount in both directions.
     * @param dx horizontal movement
     * @param dy vertical movement
     */
    void move(int dx, int dy);

}
