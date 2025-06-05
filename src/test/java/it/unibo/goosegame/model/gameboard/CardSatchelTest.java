package it.unibo.goosegame.model.gameboard;

import it.unibo.goosegame.model.cardsatchel.impl.CardSatchelModelImpl;
import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;
import it.unibo.goosegame.utilities.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests for the CardSatchelModel class.
 */
class CardSatchelTest {

    private static final int MAX_CARDS = 6;

    private CardSatchelModel satchel;

    /**
     * Sets up the test environment.
     */
    @BeforeEach
    void setUp() {
        satchel = new CardSatchelModelImpl();
    }

    /**
     * Tests the addCard method for adding a valid bonus card.
     */
    @Test
    void testAddBonusCard() {
        assertTrue(satchel.addCard(Card.NAME13)); // Use a valid bonus card
        assertEquals(1, satchel.getCards().size());
        assertTrue(satchel.getCards().contains(Card.NAME13));
    }

    /**
     * Tests the addCard method for adding a non-bonus card.
     */
    @Test
    void testAddNonBonusCard() {
        assertFalse(satchel.addCard(Card.NAME1)); // Not a bonus card
        assertEquals(0, satchel.getCards().size());
    }

    /**
     * Tests the addCard method for adding a card that is already in the satchel.
     */
    @Test
    void testAddUpToMaxCards() {
        for (int i = 0; i < MAX_CARDS; i++) {
            assertTrue(satchel.addCard(Card.NAME13));
        }
        assertEquals(MAX_CARDS, satchel.getCards().size());
        assertFalse(satchel.addCard(Card.NAME13)); // Should not add more than 6
    }

    /**
     * Tests the removeCard method for removing a valid card.
     */
    @Test
    void testRemoveCard() {
        satchel.addCard(Card.NAME13);
        assertTrue(satchel.removeCard(Card.NAME13));
        assertFalse(satchel.removeCard(Card.NAME13));
    }

    /**
     * Tests the removeCard method for removing a non-existent card.
     */
    @Test
    void testClearSatchel() {
        satchel.addCard(Card.NAME13);
        satchel.addCard(Card.NAME13);
        satchel.clear();
        assertEquals(0, satchel.getCards().size());
    }

    /**
     * Tests the isFull method for a full satchel.
     */
    @Test
    void testIsFull() {
        for (int i = 0; i < MAX_CARDS; i++) {
            satchel.addCard(Card.NAME13);
        }
        assertTrue(satchel.isFull());
        satchel.removeCard(Card.NAME13);
        assertFalse(satchel.isFull());
    }

    /**
     * Tests the getCards method for returning an unmodifiable list.
     */
    @Test
    void testGetCardsUnmodifiable() {
        satchel.addCard(Card.NAME13);
        final List<Card> cards = satchel.getCards();
        assertThrows(UnsupportedOperationException.class, () -> cards.add(Card.NAME13));
    }
}
