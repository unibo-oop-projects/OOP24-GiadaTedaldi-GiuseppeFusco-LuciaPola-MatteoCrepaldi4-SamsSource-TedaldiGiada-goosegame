package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.utilities.Position;

/**
 * Implementation of the Goose interface for the Herding Hound minigame.
 * Manages the goose's position and movement logic.
 */
public final class GooseImpl implements Goose {

    private Position position;
    private final int startX;
    private final int startY;

    /**
     * Constructs a GooseImpl object.
     * @param startX the starting X position
     * @param startY the starting Y position
     */
    public GooseImpl(final int startX, final int startY) {
        this.startX = startX;
        this.startY = startY;
        this.position = new Position(startX, startY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCoord() {
        return position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(final int dx, final int dy) {
        final int newX = position.x() + dx;
        final int newY = position.y() + dy;
        this.position = new Position(newX, newY);
    }

    /**
     * Resets the goose's position to the starting coordinates.
     */
    public void reset() {
        this.position = new Position(startX, startY);
    }
}
