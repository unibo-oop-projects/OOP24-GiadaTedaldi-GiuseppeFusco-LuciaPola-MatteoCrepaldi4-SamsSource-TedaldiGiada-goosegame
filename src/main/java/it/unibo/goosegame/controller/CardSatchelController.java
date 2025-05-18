package it.unibo.goosegame.controller;

import it.unibo.goosegame.model.CardSatchelModel;
import it.unibo.goosegame.utilities.Card;
import java.util.List;

/**
 * Controller for managing the player's card satchel (bag).
 * Acts as an interface between the model and the rest of the game logic/UI.
 */
public class CardSatchelController {
    private final CardSatchelModel satchelModel;

    /**
     * Constructs a controller with the given CardSatchelModel.
     * @param satchelModel the model to control
     */
    public CardSatchelController(CardSatchelModel satchelModel) {
        this.satchelModel = satchelModel;
    }

    /**
     * Attempts to add a card to the satchel.
     * @param card the card to add
     * @return true if added, false otherwise
     */
    public boolean addCard(Card card) {
        return satchelModel.addCard(card);
    }

    /**
     * Removes a card from the satchel.
     * @param card the card to remove
     * @return true if removed, false otherwise
     */
    public boolean removeCard(Card card) {
        return satchelModel.removeCard(card);
    }

    /**
     * Plays a card: removes it from the satchel.
     * @param card the card to play
     * @return true if the card was present and removed, false otherwise
     */
    public boolean playCard(Card card) {
        return this.removeCard(card);
    }

    /**
     * Returns an unmodifiable list of cards in the satchel.
     * @return list of cards
     */
    public List<Card> getCards() {
        return satchelModel.getCards();
    }

    /**
     * Removes all cards from the satchel.
     */
    public void clearSatchel() {
        satchelModel.clear();
    }

    /**
     * Checks if the satchel is full.
     * @return true if full
     */
    public boolean isSatchelFull() {
        return satchelModel.isFull();
    }
}
