package it.unibo.goosegame.view.cardsatchel.api;

import it.unibo.goosegame.utilities.Card;

/**
 * API for a panel that displays a single card.
 */
public interface CardPanelView {
    /**
     * Sets the card to be displayed in this panel.
     * @param card the card to display (can be null for an empty panel)
     */
    void setCard(Card card);
}