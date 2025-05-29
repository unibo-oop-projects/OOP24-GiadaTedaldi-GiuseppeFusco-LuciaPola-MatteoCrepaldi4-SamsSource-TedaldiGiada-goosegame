package it.unibo.goosegame.model.playcard;

import it.unibo.goosegame.model.playcard.api.PlayCardModel;
import it.unibo.goosegame.model.playcard.impl.PlayCardModelImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.player.impl.PlayerImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for PlayCardModel.
 */
final class PlayCardModelTest {

    private PlayCardModel model;
    private Player player;

    @BeforeEach
    void setUp() {
        model = new PlayCardModelImpl();
        player = new PlayerImpl("TestPlayer", 4);
    }

    @Test
    void testDrawCardBonusWhenWon() {
        final Card card = model.drawCard(GameState.WON);
        assertNotNull(card);
        assertTrue(card.isBonus(), "Drawn card should be a bonus when WON");
    }

    @Test
    void testDrawCardMalusWhenLost() {
        final Card card = model.drawCard(GameState.LOST);
        assertNotNull(card);
        assertFalse(card.isBonus(), "Drawn card should be a malus when LOST");
    }

    @Test
    void testCanAddToSatchelBonus() {
        final Card bonusCard = getFirstCardByType(true);
        assertTrue(model.canAddToSatchel(bonusCard, player, GameState.WON));
    }

    @Test
    void testCanAddToSatchelMalusNotThrowable() {
        final Card malusNotThrowable = getFirstMalusNotThrowable();
        assertFalse(model.canAddToSatchel(malusNotThrowable, player, GameState.LOST));
    }

    @Test
    void testCanPlayCardFromSatchelBonus() {
        final Card bonusCard = getFirstCardByType(true);
        assertTrue(model.canPlayCardFromSatchel(bonusCard, player));
    }

    @Test
    void testCanPlayCardFromSatchelMalusNotThrowable() {
        final Card malusNotThrowable = getFirstMalusNotThrowable();
        assertFalse(model.canPlayCardFromSatchel(malusNotThrowable, player));
    }

    @Test
    void testIsRemoveSelf() {
        final Card removeSelf = getFirstRemoveSelf();
        assertTrue(model.isRemoveSelf(removeSelf));
    }

    @Test
    void testIsRemoveOpponent() {
        final Card removeOpponent = getFirstRemoveOpponent();
        assertTrue(model.isRemoveOpponent(removeOpponent));
    }

    @Test
    void testIsBonusThrowable() {
        final Card bonusThrowable = getFirstBonusThrowable();
        assertTrue(model.isBonusThrowable(bonusThrowable));
    }

    @Test
    void testIsMalusNotThrowable() {
        final Card malusNotThrowable = getFirstMalusNotThrowable();
        assertTrue(model.isMalusNotThrowable(malusNotThrowable));
    }

    @Test
    void testIsBonus() {
        final Card bonusCard = getFirstCardByType(true);
        assertTrue(model.isBonus(bonusCard));
    }

    // --- Utility methods for test cards ---

    private Card getFirstCardByType(final boolean bonus) {
        for (final Card c : Card.values()) {
            if (c.isBonus() == bonus) {
                return c;
            }
        }
        fail("No card of requested type found");
        return null;
    }

    private Card getFirstMalusNotThrowable() {
        for (final Card c : Card.values()) {
            if (!c.isBonus() && !c.isThrowable()) {
                return c;
            }
        }
        fail("No malus not throwable card found");
        return null;
    }

    private Card getFirstRemoveSelf() {
        for (final Card c : Card.values()) {
            if (c.isRemove() && !c.isBonus()) {
                return c;
            }
        }
        fail("No remove self card found");
        return null;
    }

    private Card getFirstRemoveOpponent() {
        for (final Card c : Card.values()) {
            if (c.isRemove() && c.isBonus()) {
                return c;
            }
        }
        fail("No remove opponent card found");
        return null;
    }

    private Card getFirstBonusThrowable() {
        for (final Card c : Card.values()) {
            if (c.isBonus() && c.isThrowable()) {
                return c;
            }
        }
        fail("No malus throwable card found");
        return null;
    }
}
