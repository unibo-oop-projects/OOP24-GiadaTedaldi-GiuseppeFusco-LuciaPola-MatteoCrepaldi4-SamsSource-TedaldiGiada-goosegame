package it.unibo.goosegame.model.minigames.herdinghound;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the HerdingHoundModel class.
 */
public class HerdingHoundModelTest {

    /**
     * Tests that the game starts in the ONGOING state and is not over.
     */
    @Test
    void testGameStartsOngoing() {
        HerdingHoundModel model = new HerdingHoundModel(5);
        assertEquals(HerdingHoundModel.GameState.ONGOING, model.getGameState());
        assertFalse(model.isOver());
    }

    /**
     * Tests the win condition by moving the goose to the victory position.
     */
    @Test
    void testWinCondition() {
        HerdingHoundModel model = new HerdingHoundModel(3);
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
        HerdingHoundModel model = new HerdingHoundModel(5);
        model.getGoose().move(1, 0);
        model.resetGame();
        assertEquals(0, model.getGoose().getCoord().x());
        assertEquals(0, model.getGoose().getCoord().y());
    }
}
