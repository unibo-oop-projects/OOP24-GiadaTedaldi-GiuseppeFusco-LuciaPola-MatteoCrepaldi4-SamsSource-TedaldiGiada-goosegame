package it.unibo.goosegame.model.minigames.herdinghound.impl;

import it.unibo.goosegame.model.minigames.herdinghound.api.HerdingHoundModel;
import it.unibo.goosegame.model.minigames.herdinghound.api.Goose;
import it.unibo.goosegame.model.minigames.herdinghound.api.Dog;
import it.unibo.goosegame.model.minigames.herdinghound.api.Box;
import it.unibo.goosegame.utilities.Position;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Model for the Herding Hound minigame.
 * Manages the state, logic, and entities of the game.
 */
public final class HerdingHoundModelImpl implements HerdingHoundModel {
    private static final int START_X = 0;
    private static final int START_Y = 0;
    private static final long TIME_LIMIT_MS = 60_000;

    private final Goose goose;
    private final Dog dog;
    private final Box box;
    private final int gridSize;
    private long startTime;
    private boolean started;

    /**
     * Constructs a HerdingHoundModel object.
     * @param gridSize the size of the grid
     */
    public HerdingHoundModelImpl(final int gridSize) {
        this.gridSize = gridSize;
        this.goose = new GooseImpl(START_X, START_Y);
        this.dog = new DogImpl(gridSize);
        this.box = new BoxImpl(gridSize, dog);
        this.box.generateBoxes();
        this.dog.refreshDirection(goose);
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Performs the next automatic move of the goose (for demo/test).
     */
    @Override
    public void nextGooseMove() {
        final Position pos = goose.getCoord();
        final int x = pos.x();
        final int y = pos.y();
        if (y == START_Y && x < gridSize - 1) {
            goose.move(1, 0);
        } else if (x == gridSize - 1 && y < gridSize - 1) {
            goose.move(0, 1);
        } else if (y == gridSize - 1 && x > START_X) {
            goose.move(-1, 0);
        }
        dog.refreshDirection(goose);
    }

    /**
     * Returns the goose instance.
     * @return the goose instance
     */
    @Override
    public Goose getGoose() {
        return this.goose;
    }

    /**
     * Returns the dog instance.
     * @return the dog instance
     */
    @Override
    public Dog getDog() {
        return this.dog;
    }

    /**
     * Returns the box instance.
     * @return the box instance
     */
    @Override
    public Box getBox() {
        return this.box;
    }

    /**
     * Returns the grid size.
     * @return the grid size
     */
    @Override
    public int getGrid() {
        return this.gridSize;
    }

    /**
     * Returns the list of shadow positions.
     * @return list of shadow positions
     */
    @Override
    public List<Position> getShadows() {
        return this.box.getShadows();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
        goose.reset();
        dog.reset();
        box.generateBoxes();
        dog.refreshDirection(goose);
        this.started = false;
    }

    /**
     * Starts the game and resets the timer.
     */
    @Override
    public void startGame() {
        this.startTime = System.currentTimeMillis();
        this.started = true;
    }

    /**
     * Returns the remaining time for the game.
     * @return the remaining time in milliseconds
     */
    @Override
    public long getRemainingTime() {
        if (!started) {
            return TIME_LIMIT_MS;
        }
        final long elapsed = System.currentTimeMillis() - startTime;
        return Math.max(0, TIME_LIMIT_MS - elapsed);
    }

    /**
     * Returns the current game state.
     * @return the current game state
     */
    @Override
    public GameState getGameState() {
        if (getRemainingTime() == 0) {
            return GameState.LOST;
        }
        if (hasWon()) {
            return GameState.WON;
        } else if (hasLost()) {
            return GameState.LOST;
        } else {
            return GameState.ONGOING;
        }
    }

    /**
     * Indicates whether the game is over.
     * @return true if the game is finished, false otherwise
     */
    @Override
    public boolean isOver() {
        return hasWon() || hasLost() || getRemainingTime() == 0;
    }

    private boolean hasWon() {
        final Position winPos = new Position(START_X, gridSize - 1);
        return goose.getCoord().equals(winPos);
    }

    private boolean hasLost() {
        return dog.getState() == Dog.State.AWAKE && getVisible().contains(goose.getCoord());
    }

    /**
     * Returns the cells visible by the dog, excluding shadows and boxes.
     * @return list of visible positions
     */
    @Override
    public List<Position> getVisible() {
        return dog.getVisibleArea().stream()
            .filter(pos -> !box.getShadows().contains(pos))
            .filter(pos -> !box.getBoxes().contains(pos))
            .collect(Collectors.toList());
    }

    /**
     * Advances the dog's state to the next one (ASLEEP->ALERT->AWAKE->ASLEEP).
     */
    @Override
    public void nextDogState() {
        dog.refreshState();
        dog.refreshDirection(goose);
    }

    /**
     * Returns the list of box positions.
     * @return list of box positions
     */
    @Override
    public List<Position> getBoxes() {
        return box.getBoxes();
    }
}
