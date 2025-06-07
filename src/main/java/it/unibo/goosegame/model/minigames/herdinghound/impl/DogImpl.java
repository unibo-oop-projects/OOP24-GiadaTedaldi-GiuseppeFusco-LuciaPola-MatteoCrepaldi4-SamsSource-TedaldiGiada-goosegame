package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;
import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.utilities.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the Dog interface for the Herding Hound minigame.
 * Manages the dog's position, direction, state, and visible area logic.
 */
public final class DogImpl implements Dog {

    private static final int CENTER_DIVISOR = 2;
    private static final int FIRST_STEP = 1;
    private static final Direction INITIAL_DIRECTION = Direction.LEFT;
    private static final State INITIAL_STATE = State.ASLEEP;

    private final int gridSize;
    private Direction direction;
    private State state;
    private Position position;
    private final List<Position> visibleArea;

    /**
     * Constructs a DogImpl object.
     * @param gridSize the size of the grid
     */
    public DogImpl(final int gridSize) {
        this.gridSize = gridSize;
        this.position = new Position(gridSize / CENTER_DIVISOR, gridSize / CENTER_DIVISOR);
        this.direction = INITIAL_DIRECTION;
        this.state = INITIAL_STATE;
        this.visibleArea = new ArrayList<>();
    }

    /**
     * Updates the dog's direction based on the goose's position.
     * @param goose the goose instance
     */
    @Override
    public void refreshDirection(final Goose goose) {
        final int gx = goose.getCoord().x();
        final int gy = goose.getCoord().y();
        final int px = position.x();
        final int py = position.y();

        if (gx == 0 && py < this.gridSize - 1) {
            this.direction = Direction.LEFT;
        } else if (gy == this.gridSize - 1 && px < this.gridSize - 1) {
            this.direction = Direction.DOWN;
        } else if (gx == this.gridSize - 1 && py > 0) {
            this.direction = Direction.RIGHT;
        } else {
            this.direction = Direction.UP;
        }

        updateVisibleArea();
    }

    /**
     * Updates the dog's state to the next one in the cycle.
     */
    @Override
    public void refreshState() {
        switch (this.state) {
            case ASLEEP -> this.state = State.ALERT;
            case ALERT -> this.state = State.AWAKE;
            case AWAKE -> this.state = State.ASLEEP;
        }
    }

    /**
     * Returns the current state of the dog.
     * @return the current state
     */
    @Override
    public State getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCoord() {
        return new Position(this.position.x(), this.position.y());
    }

    private void updateVisibleArea() {
        this.visibleArea.clear();
        if (this.state != State.AWAKE) {
            return;
        }

        final int x = this.position.x();
        final int y = this.position.y();

        switch (this.direction) {
            case UP -> {
                for (int dy = FIRST_STEP; y - dy >= 0; dy++) {
                    for (int dx = x - dy; dx <= x + dy; dx++) {
                        if (dx >= 0 && dx < this.gridSize) {
                            this.visibleArea.add(new Position(dx, y - dy));
                        }
                    }
                }
            }
            case DOWN -> {
                for (int dy = FIRST_STEP; y + dy < this.gridSize; dy++) {
                    for (int dx = x - dy; dx <= x + dy; dx++) {
                        if (dx >= 0 && dx < this.gridSize) {
                            this.visibleArea.add(new Position(dx, y + dy));
                        }
                    }
                }
            }
            case LEFT -> {
                for (int dx = FIRST_STEP; x - dx >= 0; dx++) {
                    for (int dy = y - dx; dy <= y + dx; dy++) {
                        if (dy >= 0 && dy < this.gridSize) {
                            this.visibleArea.add(new Position(x - dx, dy));
                        }
                    }
                }
            }
            case RIGHT -> {
                for (int dx = FIRST_STEP; x + dx < this.gridSize; dx++) {
                    for (int dy = y - dx; dy <= y + dx; dy++) {
                        if (dy >= 0 && dy < this.gridSize) {
                            this.visibleArea.add(new Position(x + dx, dy));
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the list of positions visible by the dog.
     * @return list of visible positions
     */
    @Override
    public List<Position> getVisibleArea() {
        return new ArrayList<>(this.visibleArea);
    }

    /**
     * Resets the dog's position, direction, state, and visible area.
     */
    @Override
    public void reset() {
        this.position = new Position(this.gridSize / CENTER_DIVISOR, this.gridSize / CENTER_DIVISOR);
        this.direction = INITIAL_DIRECTION;
        this.state = INITIAL_STATE;
        this.visibleArea.clear();
    }
}
