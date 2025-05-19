package it.unibo.goosegame.model.cardsatchel;

import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;
import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;
import it.unibo.goosegame.utilities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardSatchelModelTest {

    private CardSatchelModel satchel;

    @BeforeEach
    void setUp() {
        satchel = new CardSatchelModelImpl();
    }

    @Test
    void testAddCardBonus() {
        Card bonusCard = getFirstCardByType(true);
        assertTrue(satchel.addCard(bonusCard));
        assertEquals(1, satchel.getCards().size());
        assertTrue(satchel.getCards().contains(bonusCard));
    }

    @Test
    void testAddCardMalusNotThrowable() {
        Card malusNotThrowable = getFirstMalusNotThrowable();
        assertFalse(satchel.addCard(malusNotThrowable));
        assertEquals(0, satchel.getCards().size());
    }

    @Test
    void testAddCardMaxLimit() {
        Card bonusCard = getFirstCardByType(true);
        for (int i = 0; i < 6; i++) {
            assertTrue(satchel.addCard(bonusCard));
        }
        assertFalse(satchel.addCard(bonusCard));
        assertEquals(6, satchel.getCards().size());
        assertTrue(satchel.isFull());
    }

    @Test
    void testRemoveCard() {
        Card bonusCard = getFirstCardByType(true);
        satchel.addCard(bonusCard);
        assertTrue(satchel.removeCard(bonusCard));
        assertEquals(0, satchel.getCards().size());
    }

    @Test
    void testRemoveCardNotPresent() {
        Card bonusCard = getFirstCardByType(true);
        assertFalse(satchel.removeCard(bonusCard));
    }

    @Test
    void testGetCardsUnmodifiable() {
        Card bonusCard = getFirstCardByType(true);
        satchel.addCard(bonusCard);
        List<Card> cards = satchel.getCards();
        assertThrows(UnsupportedOperationException.class, () -> cards.add(bonusCard));
    }

    @Test
    void testClear() {
        Card bonusCard = getFirstCardByType(true);
        satchel.addCard(bonusCard);
        satchel.clear();
        assertEquals(0, satchel.getCards().size());
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
}
