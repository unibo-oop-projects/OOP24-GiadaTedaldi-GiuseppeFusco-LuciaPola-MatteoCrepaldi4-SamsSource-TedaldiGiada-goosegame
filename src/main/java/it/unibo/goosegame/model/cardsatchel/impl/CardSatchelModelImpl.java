package it.unibo.goosegame.model.cardsatchel.impl;

import it.unibo.goosegame.utilities.Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;

/**
 * Implementation of the CardSatchelModel interface.
 * This class manages the logic for a player's card satchel (bag), including
 * adding, removing, and listing cards, as well as enforcing satchel rules.
 */
public final class CardSatchelModelImpl implements CardSatchelModel {
    private static final int MAX_CARDS = 6;
    private final List<Card> cards = new ArrayList<>();

    /**
     * Default constructor.
     */
    public CardSatchelModelImpl() {
        // No need to initialize the constructor
    }
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
        if (!card.isBonus()) {
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
