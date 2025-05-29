package it.unibo.goosegame.model.minigames.puzzle.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.utilities.Position;

/**
 * Implementation of the {@link PuzzleModel} interface.
 * This class handles the game logic for a Puzzle minigame.
 */
public class PuzzleModelImpl implements PuzzleModel {

    private static final int GRID_SIZE = 5;
    private boolean timeOver;
    private boolean shuffled;
    private Map<Position, Integer> grid;
    private Optional<Position> first;

    /**
     * Constructs a new instance of {@link PuzzleModelImpl}.
     */
    public PuzzleModelImpl() {
        this.resetGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void resetGame() {
        this.first = Optional.empty();
        this.grid = new HashMap<>();
        this.shuffled = false;
        int val = 1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid.put(new Position(i, j), val++);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameState getGameState() {
        if (this.isOver()) {
            return GameState.WON;
        }
        if (this.timeOver) {
            return GameState.LOST;
        }
        return GameState.ONGOING;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Puzzle";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        if (!this.shuffled) {
            return false;
        }
        int expected = 1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (this.grid.get(new Position(i, j)) != expected) {
                    return false;
                }
                expected++;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hit(final Position pos) {
        if (this.first.isEmpty()) {
            this.first = Optional.of(pos);
            return false;
        } else if (this.first.get().equals(pos)) {
            return false;
        } else {
            swapCell(this.first.get(), pos);
            this.first = Optional.empty();
            return true;
        }
    }

    /**
     * Swap the values of two cells in the puzzle grid at the given positions.
     * 
     * @param first the position of the first cell to swap
     * @param second the position of the second cell to swap
     */
    private void swapCell(final Position first, final Position second) {
        final int val1 = this.grid.get(first);
        final int val2 = this.grid.get(second);
        this.grid.put(first, val2);
        this.grid.put(second, val1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        final List<Integer> values = new ArrayList<>(this.grid.values());
        Collections.shuffle(values);
        final Iterator<Integer> it = values.iterator();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid.put(new Position(i, j), it.next());
            }
        }
        this.shuffled = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, Integer> getGrid() {
        return Collections.unmodifiableMap(new HashMap<>(this.grid));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeOver(final boolean timeOver) {
        this.timeOver = timeOver;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateGrid(final Map<Position, Integer> newGrid) {
        if (newGrid == null) {
            throw new IllegalArgumentException("Empty grid");
        }
        this.grid.clear();
        this.grid.putAll(newGrid);
    }

}
