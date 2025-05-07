package it.unibo.goosegame.model.minigames.memory;

import it.unibo.goosegame.model.minigames.memory.api.MemoryModel;
import it.unibo.goosegame.model.minigames.memory.impl.MemoryModelImpl;
import it.unibo.goosegame.utilities.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemoryModelImplTest {

    private MemoryModel model;

    @BeforeEach
    void setUp() {
        this.model = new MemoryModelImpl();
    }

    /**
     * Test that hitting the same position twice doesn't count as a match.
     */
    @Test
    void testIgnoreDuplicateClick() {
        final Position p = new Position(0, 0);
        model.hit(p);
        model.hit(p); 

        assertTrue(model.temporary(p).isPresent());
        assertFalse(model.found(p).isPresent());
    }

    /**
     * Test that selecting two matching positions results in them being "found".
     */
    @Test
    void testMatchingPairRevealed() {
        final Map<Integer, List<Position>> grid = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final Position pos = new Position(i, j);
                final int value = model.temporary(pos).orElseGet(() -> {
                    model.hit(pos);
                    model.hit(new Position(0, 0));
                    return model.temporary(pos).orElse(-1);
                });
                grid.computeIfAbsent(value, k -> new ArrayList<>()).add(pos);
            }
        }

        final List<Position> pair = grid.values().stream().filter(l -> l.size() >= 2).findFirst().get();
        final Position p1 = pair.get(0);
        final Position p2 = pair.get(1);

        model.hit(p1);
        model.hit(p2);

        assertTrue(model.found(p1).isPresent());
        assertTrue(model.found(p2).isPresent());
    }

    /**
     * Test that selecting two different positions temporarily reveals them.
     */
    @Test
    void testTemporaryReveal() {
        final Position p1 = new Position(0, 0);
        final Position p2 = new Position(0, 1);

        model.hit(p1);
        model.hit(p2);

        assertTrue(model.temporary(p1).isPresent());
        assertTrue(model.temporary(p2).isPresent());
    }

    /**
     * Test that the game is not over until all pairs are found.
     */
    @Test
    void testGameIsNotOverInitially() {
        assertFalse(model.isOver());
        assertEquals(0, model.getResult());
    }

    /**
     * Test the game reports over once all pairs are found.
     */
    @Test
    void testGameOverAfterAllPairsFound() {
        final Map<Integer, List<Position>> unmatched = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                final Position p = new Position(i, j);
                final int val = model.temporary(p).orElseGet(() -> {
                    model.hit(p);
                    model.hit(new Position(0, 0));
                    return model.temporary(p).orElse(-1);
                });
                unmatched.computeIfAbsent(val, k -> new ArrayList<>()).add(p);
            }
        }

        for (final List<Position> pair : unmatched.values()) {
            if (pair.size() >= 2) {
                model.hit(pair.get(0));
                model.hit(pair.get(1));
            }
        }

        assertTrue(model.isOver());
        assertEquals(1, model.getResult());
    }
}

