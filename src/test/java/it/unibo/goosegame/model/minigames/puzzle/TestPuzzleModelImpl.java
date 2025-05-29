package it.unibo.goosegame.model.minigames.puzzle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.minigames.puzzle.api.PuzzleModel;
import it.unibo.goosegame.model.minigames.puzzle.impl.PuzzleModelImpl;
import it.unibo.goosegame.utilities.Position;
/**
 * JUnit tests for {@link PuzzleModelImpl}.
 */
class TestPuzzleModelImpl {

    private static final int GRID_SIZE = 5;
    private PuzzleModel model;

    /**
     * Initializes a new PuzzleModel before each test.
     */
    @BeforeEach
    void init() {
        this.model = new PuzzleModelImpl();
    }
    /**
     * Tests that resetGame() correctly reinitializes the grid to a solved state.
     */
    @Test
    void testResetGame() {
        this.model.shuffle();
        this.model.resetGame();
        int expected = 1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                assertEquals(expected++, this.model.getGrid().get(new Position(i, j)));
            }
        }
        assertEquals(GameState.ONGOING, this.model.getGameState());
        assertFalse(this.model.isOver());
    }

    /**
     * Tests that shuffling the grid disrupts the correct order.
     */
    @Test
    void testShuffleGrid() {
        this.model.shuffle();
        assertFalse(this.model.isOver());
    }

    /**
     * Tests that hitting two different positions correctly swaps their values.
     */
    @Test
    void testHitandSwap() {
        final Position pos1 = new Position(0, 0);
        final Position pos2 = new Position(0, 1);
        final int val1 = this.model.getGrid().get(pos1);
        final int val2 = this.model.getGrid().get(pos2);
        this.model.hit(pos1);
        this.model.hit(pos2);
        assertEquals(val2, this.model.getGrid().get(pos1));
        assertEquals(val1, this.model.getGrid().get(pos2));
    }

    /**
     * Tests that the result is WON when the puzzle is correctly solved in time.
     */
    @Test
    void testGetResultWin() {
        final Map<Position, Integer> newGrid = new HashMap<>();
        this.model.shuffle();
        int val = 1;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                final Position addPos = new Position(i, j);
                newGrid.put(addPos, val++);
            }
        }
        this.model.updateGrid(newGrid);
        assertTrue(this.model.isOver());
        assertEquals(GameState.WON, this.model.getGameState());
    }

    /**
     * Tests that the result is LOST when the time is over.
     */
    @Test
    void testGetResultTimeOver() {
        this.model.shuffle();
        this.model.setTimeOver(true);
        assertEquals(GameState.LOST, this.model.getGameState());
    }
}
