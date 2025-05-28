package it.unibo.goosegame.view.cardsatchel.api;

import it.unibo.goosegame.utilities.Card;
import java.util.List;

/**
 * API for the view that displays and manages the player's card satchel.
 */
public interface CardSatchelView {
    /**
     * Updates the cards shown in the satchel view.
     * @param cards the list of cards to display
     */
    void updateCards(List<Card> cards);
}
