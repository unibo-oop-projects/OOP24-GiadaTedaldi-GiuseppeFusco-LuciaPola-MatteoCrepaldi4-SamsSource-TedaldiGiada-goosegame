package it.unibo.goosegame.model.minigames.tris;

import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.model.minigames.tris.impl.TrisModelImpl;
import it.unibo.goosegame.utilities.Position;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link TrisModelImpl}.
 */
public class TestTrisModelImpl {

    private TrisModel model;

    /**
     * Initializes a new TrisModel before each test.
     */
    @BeforeEach
    void init() {
        model = new TrisModelImpl();
    }

    /**
     * Tests the initial state of the game after construction.
     */
    @Test
    void testInit() {
        assertFalse(model.isOver());
        assertFalse(model.isFull());
        assertEquals("Your turn!", model.getStatus());
    }

    /**
     * Tests that a human move is correctly registered and disallows duplicate moves. 
     */
    @Test
    void testMakeHumanMove() {
        Position pos = new Position(0, 0);
        assertTrue(model.makeHumanMove(pos));
        assertTrue(model.isHuman(pos));
        assertFalse(model.makeHumanMove(pos));
    }

    /**
     * Tests that PC makes a valid move after the human's turn.
     */
    @Test
    void testMakePCMove() {
        Position pos = new Position(0, 0);
        model.makeHumanMove(pos);
        model.makePcMove();

        boolean pcMoved = false;
        for(int i=0; i<3 && !pcMoved; i++) {
            for(int j=0; j<3; j++) {
                Position p = new Position(i, j);
                if(model.isPc(p)) {
                    pcMoved = true;
                    break;
                }

            }
        }
        assertTrue(pcMoved, "The PC should have moved.");
    }

    /**
     * Tests a win condition for the human player.
     */
    @Test
    void testWin() {
        TestUtils.forceMove(model, new Position(0, 0), true);
        TestUtils.forceMove(model, new Position(0, 1), true);
        TestUtils.forceMove(model, new Position(0, 2), true);

        assertTrue(model.checkWin());
        assertTrue(model.isOver());
        assertEquals(-1, model.getResult());  
    }

    /**
     * Tests a draw condition: full grid with no winners.
     */
    @Test
    void testDraw() {
        TestUtils.forceMove(model, new Position(0, 0), true);
        TestUtils.forceMove(model, new Position(0, 1), false);
        TestUtils.forceMove(model, new Position(0, 2), true);
        TestUtils.forceMove(model, new Position(1, 0), true);
        TestUtils.forceMove(model, new Position(1, 1), true);
        TestUtils.forceMove(model, new Position(1, 2), false);
        TestUtils.forceMove(model, new Position(2, 0), false);
        TestUtils.forceMove(model, new Position(2, 1), true);
        TestUtils.forceMove(model, new Position(2, 2), false);

        assertTrue(model.isFull());
        assertTrue(model.isOver());
        assertFalse(model.checkWin());
        assertEquals(3, model.getResult());
    }

    /**
     * Tests that the game reset properly, clearing the grid and restoring the initial state.
     */
    @Test
    void testResetGamed() {
        model.makeHumanMove(new Position(0, 0));
        model.resetGame();

        assertFalse(model.isFull());
        assertFalse(model.isOver());
        assertEquals("Your turn!", model.getStatus());
    }

    /**
     * Utility class to assist with test setup by directly modifying the game state.
     */
    private static class TestUtils {
        /**
        * Places a piece at the specified position directly into the model,
        * bypassing turn logic. Intended for tests setup only.
        *
        * @param model  the TrisModel instance to modify 
        * @param pos    the position on the board
        * @param isHuman true if placing a human move, false for PC
        */
        public static void forceMove(TrisModel model, Position pos, boolean isHuman) {
            model.getGrid().put(pos, isHuman? TrisModel.Player.HUMAN : TrisModel.Player.PC);
        }
    }
}
