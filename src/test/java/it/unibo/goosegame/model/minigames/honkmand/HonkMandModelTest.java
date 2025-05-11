package it.unibo.goosegame.model.minigames.honkmand;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import it.unibo.goosegame.utilities.Colors;

public class HonkMandModelTest {

    @Test
    void testStartGameInitializesState() {
        HonkMandModel model = new HonkMandModel();
        model.startGame();
        assertEquals(1, model.getLevel());
        assertEquals(0, model.getScore());
        assertEquals(HonkMandModel.GameState.PLAYING, model.getGameState());
        assertFalse(model.getSequence().isEmpty());
    }

    @Test
    void testCorrectInputAdvancesRound() {
        HonkMandModel model = new HonkMandModel();
        model.startGame();
        Colors first = model.getSequence().get(0);
        assertEquals(HonkMandModel.InputResult.NEXT_ROUND, model.checkPlayerInput(first));
    }

    @Test
    void testWrongInputEndsGame() {
        HonkMandModel model = new HonkMandModel();
        model.startGame();
        Colors wrong = Colors.values()[(model.getSequence().get(0).ordinal() + 1) % Colors.values().length];
        assertEquals(HonkMandModel.InputResult.GAME_OVER, model.checkPlayerInput(wrong));
    }
}
