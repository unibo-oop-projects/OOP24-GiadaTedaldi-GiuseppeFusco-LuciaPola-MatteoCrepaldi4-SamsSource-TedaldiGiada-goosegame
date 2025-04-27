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
public class PuzzleModelImpl implements PuzzleModel{

    private final static int GRID_SIZE = 5;
    private boolean timeOver = false;
    private Map<Position,Integer> grid;
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
    public void resetGame() {
        this.first = Optional.empty();
        this.grid = new HashMap<>();
        int val = 1;
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                this.grid.put(new Position(i, j), val++);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getResult() {
        if(!this.isOver() || this.timeOver) {
            return 0;
        }
        return 1;
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
        int expected = 1;
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                if(this.grid.get(new Position(i, j)) != expected++) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hit(Position pos) {
        if(this.first.isEmpty()) {
            this.first = Optional.of(pos);
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
    private void swapCell(Position first, Position second) {
        int val1 = this.grid.get(first);
        int val2 = this.grid.get(second);
        this.grid.put(first, val2);
        this.grid.put(second, val1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shuffle() {
        List<Integer> values = new ArrayList<>(this.grid.values());
        Collections.shuffle(values);
        Iterator<Integer> it = values.iterator();
        for(int i=0; i<GRID_SIZE; i++) {
            for(int j=0; j<GRID_SIZE; j++) {
                this.grid.put(new Position(i, j), it.next());
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Position, Integer> getGrid() {
        return this.grid;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTimeOver(boolean timeOver) {
        this.timeOver = timeOver;
    }

}
