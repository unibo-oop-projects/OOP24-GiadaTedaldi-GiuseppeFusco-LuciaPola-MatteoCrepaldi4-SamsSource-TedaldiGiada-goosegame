package it.unibo.goosegame.model.minigames.memory.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.utilities.Position;

/**
 * Implementation of the {@link MemoryModel} interface.
 * This class represents the logic of the "Memory" mini-game,
 * managing cell values, selections, matches, and game status.
 */
public class MemoryModelImpl implements MemoryModel {
    private static final int SIZE = 4;
    private final Map<Position, Integer> values = new HashMap<>();
    private final Set<Position> shown = new HashSet<>();
    private final List<Position> selected = new ArrayList<>();

    /**
     * Constructor.
     * Initializes the game board with random values.
     */
    public MemoryModelImpl() {
        final List<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        int index = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                values.put(new Position(i, j), numbers.get(index));
                index++;
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void hit(final Position p) {
        if (this.selected.contains(p)) {
            return;
        }
        if (this.selected.size() == 2) {
            this.selected.clear();
        }
        this.selected.add(p);
        if (this.selected.size() == 2
                && this.values.get(this.selected.get(0)).equals(this.values.get(this.selected.get(1)))) {
                this.shown.add(this.selected.get(0));
                this.shown.add(this.selected.get(1));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> found(final Position p) {
        return Optional.of(p).filter(this.shown::contains).map(this.values::get);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Integer> temporary(final Position p) {
        return Optional.of(p).filter(this.selected::contains).map(this.values::get);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isOver() {
        final List<Integer> remaining = values.keySet().stream().filter(p -> !this.shown.contains(p)).map(values::get).toList();
        return remaining.stream().distinct().count() == remaining.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetGame() {
    }

    /**
     * {@inheritDoc}
     */
    @Override 
    public int getResult() {
        return shown.size() == values.size() ? 1 : 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return "Memory Game";
    }
}
