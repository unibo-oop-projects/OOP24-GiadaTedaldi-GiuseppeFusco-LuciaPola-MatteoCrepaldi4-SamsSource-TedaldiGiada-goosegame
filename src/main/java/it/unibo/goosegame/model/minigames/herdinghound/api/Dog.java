package it.unibo.goosegame.model.minigames.herdinghound.api;

import it.unibo.goosegame.utilities.Position;

/**
 * Represents the dog in the Herding Hound minigame.
 * Manages position, direction, and state.
 */
public interface Dog {

    /**
     * Possible directions for the dog.
     */
    enum Direction {
        /** The dog is facing up. */
        UP,
        /** The dog is facing down. */
        DOWN,
        /** The dog is facing left. */
        LEFT,
        /** The dog is facing right. */
        RIGHT
    }

    /**
     * Possible states for the dog.
     */
    enum State {
        /** The dog is asleep. */
        ASLEEP,
        /** The dog is alert. */
        ALERT,
        /** The dog is awake. */
        AWAKE
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
