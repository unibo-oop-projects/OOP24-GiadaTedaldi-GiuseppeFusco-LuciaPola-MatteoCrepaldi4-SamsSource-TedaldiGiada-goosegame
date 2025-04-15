package it.unibo.goosegame.model.minigames.tris;

import it.unibo.goosegame.model.minigames.tris.api.TrisModel;
import it.unibo.goosegame.model.minigames.tris.impl.TrisModelImpl;
import it.unibo.goosegame.utilities.Position;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestTrisModelImpl {

    private TrisModel model;

    @BeforeEach
    void init() {
        model = new TrisModelImpl();
    }

    @Test
    void testInit() {
        assertFalse(model.isOver());
        assertFalse(model.isFull());
        assertEquals("Your turn!", model.getStatus());
    }

    @Test
    void testMakeHumanMove() {
        Position pos = new Position(0, 0);
        assertTrue(model.makeHumanMove(pos));
        assertTrue(model.isHuman(pos));
        assertFalse(model.makeHumanMove(pos));
    }

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

    @Test
    void testWin() {
        TestUtils.forceMove(model, new Position(0, 0), true);
        TestUtils.forceMove(model, new Position(0, 1), true);
        TestUtils.forceMove(model, new Position(0, 2), true);

        assertTrue(model.checkWin());
        assertTrue(model.isOver());
        assertEquals(-1, model.getResult());  
    }

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

    @Test
    void testResetGamed() {
        model.makeHumanMove(new Position(0, 0));
        model.resetGame();

        assertFalse(model.isFull());
        assertFalse(model.isOver());
        assertEquals("Your turn!", model.getStatus());
    }

    private static class TestUtils {
        public static void forceMove(TrisModel model, Position pos, boolean isHuman) {
            model.getGrid().put(pos, isHuman? TrisModel.Player.HUMAN : TrisModel.Player.PC);
        }
    }
}
