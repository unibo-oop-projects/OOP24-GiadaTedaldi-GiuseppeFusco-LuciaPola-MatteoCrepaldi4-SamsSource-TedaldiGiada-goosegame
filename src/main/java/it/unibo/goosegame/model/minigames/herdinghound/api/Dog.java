package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Position;

/**
 * Represents the dog in the Herding Hound minigame.
 * Manages position, direction, and state.
 */
public interface Dog{

    /**
     * Possible directions for the dog.
     */
    public enum Direction {
       UP, DOWN,LEFT, RIGHT
    }
    /**
     * Possible states for the dog.
     */
    public enum State {
        ASLEEP, ALERT, AWAKE
    }
    
    /**
     * Returns the current position of the dog.
     * @return the current position
     */
    Position getCoord();

    /**
     * Returns the current direction of the dog.
     * @return the direction
     */
    Direction getDirection();
}