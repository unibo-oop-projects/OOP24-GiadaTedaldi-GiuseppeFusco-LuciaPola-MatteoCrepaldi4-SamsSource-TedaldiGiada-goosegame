package it.unibo.goosegame.model.minigames.herdinghound.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;
import it.unibo.goosegame.utilities.Pair;

public class DogImpl implements Dog {

    private static final int CENTER_DIVISOR = 2;
    private static final int FIRST_STEP = 1;
    private static final Direction INITIAL_DIRECTION = Direction.LEFT;
    private static final State INITIAL_STATE = State.ASLEEP;

    private final int gridSize;
    private Direction direction;
    private State state;
    private Pair<Integer, Integer> position;
    private final List<Pair<Integer, Integer>> visibleArea;

    public DogImpl(int gridSize) {
        this.gridSize = gridSize;
        this.position = new Pair<>(gridSize / CENTER_DIVISOR, gridSize / CENTER_DIVISOR);
        this.direction = INITIAL_DIRECTION;
        this.state = INITIAL_STATE;
        this.visibleArea = new ArrayList<>();
    }

    public void refreshDirection(GooseImpl goose) {
        int gx = goose.getCoord().getX();
        int gy = goose.getCoord().getY();
        int px = position.getX();
        int py = position.getY();

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

    public void refreshState() {
        switch (state) {
            case ASLEEP -> state = State.ALERT;
            case ALERT -> state = State.AWAKE;
            case AWAKE -> state = State.ASLEEP;
        }
    }

    public State getState() {
        return this.state;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

    @Override
    public Pair<Integer, Integer> getCoord() {
        return new Pair<>(position.getX(), position.getY());
    }

    private void updateVisibleArea() {
        visibleArea.clear();
        if (state != State.AWAKE) {
            return;
        }

        int x = position.getX();
        int y = position.getY();

        switch (direction) {
            case UP -> {
                for (int dy = FIRST_STEP; y - dy >= 0; dy++) {
                    for (int dx = x - dy; dx <= x + dy; dx++) {
                        if (dx >= 0 && dx < gridSize) {
                            visibleArea.add(new Pair<>(dx, y - dy));
                        }
                    }
                }
            }
            case DOWN -> {
                for (int dy = FIRST_STEP; y + dy < gridSize; dy++) {
                    for (int dx = x - dy; dx <= x + dy; dx++) {
                        if (dx >= 0 && dx < gridSize) {
                            visibleArea.add(new Pair<>(dx, y + dy));
                        }
                    }
                }
            }
            case LEFT -> {
                for (int dx = FIRST_STEP; x - dx >= 0; dx++) {
                    for (int dy = y - dx; dy <= y + dx; dy++) {
                        if (dy >= 0 && dy < gridSize) {
                            visibleArea.add(new Pair<>(x - dx, dy));
                        }
                    }
                }
            }
            case RIGHT -> {
                for (int dx = FIRST_STEP; x + dx < gridSize; dx++) {
                    for (int dy = y - dx; dy <= y + dx; dy++) {
                        if (dy >= 0 && dy < gridSize) {
                            visibleArea.add(new Pair<>(x + dx, dy));
                        }
                    }
                }
            }
        }
    }

    public List<Pair<Integer, Integer>> getVisibleArea() {
        return new ArrayList<>(visibleArea);
    }

    public void reset() {
        this.position = new Pair<>(gridSize / CENTER_DIVISOR, gridSize / CENTER_DIVISOR);
        this.direction = INITIAL_DIRECTION;
        this.state = INITIAL_STATE;
        this.visibleArea.clear();
    }
}


