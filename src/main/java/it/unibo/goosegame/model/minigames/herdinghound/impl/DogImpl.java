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

        if (gx == 0 && py < gridSize - 1) {
            direction = Direction.LEFT;
        } else if (gy == gridSize - 1 && px < gridSize - 1) {
            direction = Direction.DOWN;
        } else if (gx == gridSize - 1 && py > 0) {
            direction = Direction.RIGHT;
        } else {
            direction = Direction.UP;
        }

        updateVisibleArea();
    }

    /**
     * Updates the dog's state to the next one in the cycle.
     */
    @Override
    public void refreshState() {
        switch (state) {
            case ASLEEP -> state = State.ALERT;
            case ALERT -> state = State.AWAKE;
            case AWAKE -> state = State.ASLEEP;
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
        return direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Position getCoord() {
        return new Position(position.x(), position.y());
    }

    private void updateVisibleArea() {
        visibleArea.clear();
        if (state != State.AWAKE) {
            return;
        }

        final int x = position.x();
        final int y = position.y();

        switch (direction) {
            case UP -> {
                for (int dy = FIRST_STEP; y - dy >= 0; dy++) {
                    for (int dx = x - dy; dx <= x + dy; dx++) {
                        if (dx >= 0 && dx < gridSize) {
                            visibleArea.add(new Position(dx, y - dy));
                        }
                    }
                }
            }
            case DOWN -> {
                for (int dy = FIRST_STEP; y + dy < gridSize; dy++) {
                    for (int dx = x - dy; dx <= x + dy; dx++) {
                        if (dx >= 0 && dx < gridSize) {
                            visibleArea.add(new Position(dx, y + dy));
                        }
                    }
                }
            }
            case LEFT -> {
                for (int dx = FIRST_STEP; x - dx >= 0; dx++) {
                    for (int dy = y - dx; dy <= y + dx; dy++) {
                        if (dy >= 0 && dy < gridSize) {
                            visibleArea.add(new Position(x - dx, dy));
                        }
                    }
                }
            }
            case RIGHT -> {
                for (int dx = FIRST_STEP; x + dx < gridSize; dx++) {
                    for (int dy = y - dx; dy <= y + dx; dy++) {
                        if (dy >= 0 && dy < gridSize) {
                            visibleArea.add(new Position(x + dx, dy));
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
        return new ArrayList<>(visibleArea);
    }

    /**
     * Resets the dog's position, direction, state, and visible area.
     */
    @Override
    public void reset() {
        this.position = new Position(gridSize / CENTER_DIVISOR, gridSize / CENTER_DIVISOR);
        this.direction = INITIAL_DIRECTION;
        this.state = INITIAL_STATE;
        this.visibleArea.clear();
    }
}
