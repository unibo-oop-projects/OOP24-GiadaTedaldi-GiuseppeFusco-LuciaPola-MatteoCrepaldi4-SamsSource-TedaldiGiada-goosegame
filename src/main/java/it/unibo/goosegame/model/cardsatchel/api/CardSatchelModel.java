package it.unibo.goosegame.model.cardsatchel.api;

import it.unibo.goosegame.utilities.Card;
import java.util.List;

/**
 * API for the model that manages the player's card satchel (bag).
 */
public interface CardSatchelModel {
    /**
     * Attempts to add a card to the satchel.
     * @param card the card to add
     * @return true if added, false otherwise
     */
    boolean addCard(Card card);

    /**
     * Removes a card from the satchel.
     * @param card the card to remove
     * @return true if removed, false if not present
     */
    boolean removeCard(Card card);

    /**
     * Returns an unmodifiable list of cards in the satchel.
     * @return list of cards
     */
    List<Card> getCards();

    /**
     * Checks if the satchel is full.
     * @return true if full
     */
    boolean isFull();

    /**
     * Removes all cards from the satchel.
     */
    void clear();
}