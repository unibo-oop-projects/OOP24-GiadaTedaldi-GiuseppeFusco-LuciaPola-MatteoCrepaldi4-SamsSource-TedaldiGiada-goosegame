package it.unibo.goosegame.model.minigames.honkmand;

import it.unibo.goosegame.model.minigames.honkmand.impl.HonkMandModelImpl;
import it.unibo.goosegame.utilities.Colors;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the HonkMandModel class.
 */
class HonkMandModelTest {

    /**
     * Tests that starting the game initializes the state correctly.
     */
    @Test
    void testStartGameInitializesState() {
        final HonkMandModelImpl model = new HonkMandModelImpl();
        model.startGame();
        assertEquals(1, model.getLevel());
        assertEquals(0, model.getScore());
        assertEquals(HonkMandModelImpl.GameState.ONGOING, model.getGameState());
        assertFalse(model.getSequence().isEmpty());
    }

    /**
     * Tests that the correct input advances the round.
     */
    @Test
    void testCorrectInputAdvancesRound() {
        final HonkMandModelImpl model = new HonkMandModelImpl();
        model.startGame();
        final Colors first = model.getSequence().get(0);
        assertEquals(HonkMandModelImpl.InputResult.NEXT_ROUND, model.checkPlayerInput(first));
    }

    /**
     * Tests that a wrong input ends the game.
     */
    @Test
    void testWrongInputEndsGame() {
        final HonkMandModelImpl model = new HonkMandModelImpl();
        model.startGame();
        final Colors wrong = Colors.values()[(model.getSequence().get(0).ordinal() + 1) % Colors.values().length];
        assertEquals(HonkMandModelImpl.InputResult.GAME_OVER, model.checkPlayerInput(wrong));
    }
}
