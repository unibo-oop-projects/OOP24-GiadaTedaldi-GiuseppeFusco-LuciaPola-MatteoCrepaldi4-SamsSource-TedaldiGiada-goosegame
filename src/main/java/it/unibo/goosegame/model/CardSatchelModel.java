package it.unibo.goosegame.model;

import it.unibo.goosegame.utilities.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Model for the player's card satchel (bag).
 * Manages a list of cards with rules:
 * - Max 6 cards
 * - Malus cards (bonus == false) that are not throwable cannot be added
 */
public class CardSatchelModel {
    private static final int MAX_CARDS = 6;
    private final List<Card> cards = new ArrayList<>();

    /**
     * Attempts to add a card to the satchel.
     * @param card the card to add
     * @return true if added, false otherwise
     */
    public boolean addCard(Card card) {
        if (cards.size() >= MAX_CARDS) {
            return false;
        }
        // If malus and not throwable, cannot add
        if (!card.isBonus() && !card.isThrowable()) {
            return false;
        }
        cards.add(card);
        return true;
    }

    /**
     * Removes a card from the satchel.
     * @param card the card to remove
     * @return true if removed, false if not present
     */
    public boolean removeCard(Card card) {
        return cards.remove(card);
    }

    /**
     * Returns an unmodifiable list of cards in the satchel.
     * @return list of cards
     */
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Checks if the satchel is full.
     * @return true if full
     */
    public boolean isFull() {
        return cards.size() >= MAX_CARDS;
    }

    /**
     * Removes all cards from the satchel.
     */
    public void clear() {
        cards.clear();
    }
}
