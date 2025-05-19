package it.unibo.goosegame.model.playcard;

import it.unibo.goosegame.model.playcard.api.PlayCardModel;
import it.unibo.goosegame.model.playcard.impl.PlayCardModelImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayCardModelTest {

    private PlayCardModel model;
    private Player player;

    @BeforeEach
    void setUp() {
        model = new PlayCardModelImpl();
        player = new Player("TestPlayer");
    }

    @Test
    void testDrawCardBonusWhenWon() {
        Card card = model.drawCard(GameState.WON);
        assertNotNull(card);
        assertTrue(card.isBonus(), "Drawn card should be a bonus when WON");
    }

    @Test
    void testDrawCardMalusWhenLost() {
        Card card = model.drawCard(GameState.LOST);
        assertNotNull(card);
        assertFalse(card.isBonus(), "Drawn card should be a malus when LOST");
    }

    @Test
    void testCanAddToSatchelBonus() {
        Card bonusCard = getFirstCardByType(true);
        assertTrue(model.canAddToSatchel(bonusCard, player, GameState.WON));
    }

    @Test
    void testCanAddToSatchelMalusNotThrowable() {
        Card malusNotThrowable = getFirstMalusNotThrowable();
        assertFalse(model.canAddToSatchel(malusNotThrowable, player, GameState.LOST));
    }

    @Test
    void testCanPlayCardFromSatchelBonus() {
        Card bonusCard = getFirstCardByType(true);
        assertTrue(model.canPlayCardFromSatchel(bonusCard, player));
    }

    @Test
    void testCanPlayCardFromSatchelMalusNotThrowable() {
        Card malusNotThrowable = getFirstMalusNotThrowable();
        assertFalse(model.canPlayCardFromSatchel(malusNotThrowable, player));
    }

    @Test
    void testIsRemoveSelf() {
        Card removeSelf = getFirstRemoveSelf();
        assertTrue(model.isRemoveSelf(removeSelf));
    }

    @Test
    void testIsRemoveOpponent() {
        Card removeOpponent = getFirstRemoveOpponent();
        assertTrue(model.isRemoveOpponent(removeOpponent));
    }

    @Test
    void testIsMalusThrowable() {
        Card malusThrowable = getFirstMalusThrowable();
        assertTrue(model.isMalusThrowable(malusThrowable));
    }

    @Test
    void testIsMalusNotThrowable() {
        Card malusNotThrowable = getFirstMalusNotThrowable();
        assertTrue(model.isMalusNotThrowable(malusNotThrowable));
    }

    @Test
    void testIsBonus() {
        Card bonusCard = getFirstCardByType(true);
        assertTrue(model.isBonus(bonusCard));
    }

    // --- Utility methods for test cards ---

    private Card getFirstCardByType(boolean bonus) {
        for (Card c : Card.values()) {
            if (c.isBonus() == bonus) return c;
        }
        fail("No card of requested type found");
        return null;
    }

    private Card getFirstMalusNotThrowable() {
        for (Card c : Card.values()) {
            if (!c.isBonus() && !c.isThrowable()) return c;
        }
        fail("No malus not throwable card found");
        return null;
    }

    private Card getFirstRemoveSelf() {
        for (Card c : Card.values()) {
            if (c.isRemove() && !c.isBonus()) return c;
        }
        fail("No remove self card found");
        return null;
    }

    private Card getFirstRemoveOpponent() {
        for (Card c : Card.values()) {
            if (c.isRemove() && c.isBonus()) return c;
        }
        fail("No remove opponent card found");
        return null;
    }

    private Card getFirstMalusThrowable() {
        for (Card c : Card.values()) {
            if (!c.isBonus() && c.isThrowable()) return c;
        }
        fail("No malus throwable card found");
        return null;
    }
}
