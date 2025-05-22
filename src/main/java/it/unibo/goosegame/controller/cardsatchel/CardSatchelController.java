package it.unibo.goosegame.controller.cardsatchel;

import it.unibo.goosegame.model.cardsatchel.api.CardSatchelModel;
import it.unibo.goosegame.utilities.Card;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

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
    @SuppressFBWarnings(
    value = "EI2",
    justification = "The controller must operate on the same externally provided model to maintain state consistency."
)
    public CardSatchelController(final CardSatchelModel satchelModel) {
        this.satchelModel = satchelModel;
    }

    /**
     * Attempts to add a card to the satchel.
     * @param card the card to add
     * @return true if added, false otherwise
     */
    public boolean addCard(final Card card) {
        return satchelModel.addCard(card);
    }

    /**
     * Removes a card from the satchel.
     * @param card the card to remove
     * @return true if removed, false otherwise
     */
    public boolean removeCard(final Card card) {
        return satchelModel.removeCard(card);
    }

    /**
     * Plays a card: removes it from the satchel.
     * @param card the card to play
     * @return true if the card was present and removed, false otherwise
     */
    public boolean playCard(final Card card) {
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
