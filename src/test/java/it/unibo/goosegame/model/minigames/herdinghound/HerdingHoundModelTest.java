package it.unibo.goosegame.model.minigames.herdinghound;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the HerdingHoundModel class.
 */
class HerdingHoundModelTest {

    private static final int GRID_SIZE_5 = 5;
    private static final int GRID_SIZE_3 = 3;

    /**
     * Tests that the game starts in the ONGOING state and is not over.
     */
    @Test
    void testGameStartsOngoing() {
        final HerdingHoundModel model = new HerdingHoundModel(GRID_SIZE_5);
        assertEquals(HerdingHoundModel.GameState.ONGOING, model.getGameState());
        assertFalse(model.isOver());
    }

    /**
     * Tests the win condition by moving the goose to the victory position.
     */
    @Test
    void testWinCondition() {
        final HerdingHoundModel model = new HerdingHoundModel(GRID_SIZE_3);
        // Move the goose to the winning position
        model.getGoose().move(0, 2);
        assertEquals(HerdingHoundModel.GameState.WON, model.getGameState());
        assertTrue(model.isOver());
    }

    /**
     * Tests that the game is correctly reset to the initial state.
     */
    @Test
    void testResetGame() {
        final HerdingHoundModel model = new HerdingHoundModel(GRID_SIZE_5);
        model.getGoose().move(1, 0);
        model.resetGame();
        assertEquals(0, model.getGoose().getCoord().x());
        assertEquals(0, model.getGoose().getCoord().y());
    }
}
