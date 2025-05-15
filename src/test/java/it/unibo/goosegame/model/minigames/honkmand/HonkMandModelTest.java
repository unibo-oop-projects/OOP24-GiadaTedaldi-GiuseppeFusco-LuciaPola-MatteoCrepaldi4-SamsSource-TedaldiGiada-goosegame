package it.unibo.goosegame.model.minigames.honkmand;

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
        final HonkMandModel model = new HonkMandModel();
        model.startGame();
        assertEquals(1, model.getLevel());
        assertEquals(0, model.getScore());
        assertEquals(HonkMandModel.GameState.PLAYING, model.getGameState());
        assertFalse(model.getSequence().isEmpty());
    }

    /**
     * Tests that the correct input advances the round.
     */
    @Test
    void testCorrectInputAdvancesRound() {
        final HonkMandModel model = new HonkMandModel();
        model.startGame();
        final Colors first = model.getSequence().get(0);
        assertEquals(HonkMandModel.InputResult.NEXT_ROUND, model.checkPlayerInput(first));
    }

    /**
     * Tests that a wrong input ends the game.
     */
    @Test
    void testWrongInputEndsGame() {
        final HonkMandModel model = new HonkMandModel();
        model.startGame();
        final Colors wrong = Colors.values()[(model.getSequence().get(0).ordinal() + 1) % Colors.values().length];
        assertEquals(HonkMandModel.InputResult.GAME_OVER, model.checkPlayerInput(wrong));
    }
}
