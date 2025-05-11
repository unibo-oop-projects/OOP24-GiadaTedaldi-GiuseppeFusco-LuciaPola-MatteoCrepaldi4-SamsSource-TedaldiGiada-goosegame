package it.unibo.goosegame.model.minigames.herdinghound;

import it.unibo.goosegame.model.minigames.herdinghound.impl.HerdingHoundModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HerdingHoundModelTest {

    @Test
    void testGameStartsOngoing() {
        HerdingHoundModel model = new HerdingHoundModel(5);
        assertEquals(HerdingHoundModel.GameState.ONGOING, model.getGameState());
        assertFalse(model.isOver());
    }

    @Test
    void testWinCondition() {
        HerdingHoundModel model = new HerdingHoundModel(3);
        // Porta l'oca alla posizione di vittoria
        model.getGoose().move(0, 2);
        assertEquals(HerdingHoundModel.GameState.WON, model.getGameState());
        assertTrue(model.isOver());
    }

    @Test
    void testResetGame() {
        HerdingHoundModel model = new HerdingHoundModel(5);
        model.getGoose().move(1, 0);
        model.resetGame();
        assertEquals(0, model.getGoose().getCoord().x());
        assertEquals(0, model.getGoose().getCoord().y());
    }
}
