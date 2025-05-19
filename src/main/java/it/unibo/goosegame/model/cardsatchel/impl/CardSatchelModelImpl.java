package it.unibo.goosegame.model.cardsatchel.impl;

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
import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;

public final class CardSatchelModelImpl implements CardSatchelModel {
    private static final int MAX_CARDS = 6;
    private final List<Card> cards = new ArrayList<>();

    /**
     * Attempts to add a card to the satchel.
     * @param card the card to add
     * @return true if added, false otherwise
     */
    @Override
    public boolean addCard(final Card card) {
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
    @Override
    public boolean removeCard(final Card card) {
        return cards.remove(card);
    }

    /**
     * Returns an unmodifiable list of cards in the satchel.
     * @return list of cards
     */
    @Override
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }

    /**
     * Checks if the satchel is full.
     * @return true if full
     */
    @Override
    public boolean isFull() {
        return cards.size() >= MAX_CARDS;
    }

    /**
     * Removes all cards from the satchel.
     */
    @Override
    public void clear() {
        cards.clear();
    }
}
