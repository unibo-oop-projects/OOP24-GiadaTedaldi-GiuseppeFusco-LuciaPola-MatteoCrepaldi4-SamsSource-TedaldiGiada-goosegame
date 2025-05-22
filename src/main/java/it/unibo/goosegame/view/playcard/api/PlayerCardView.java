package it.unibo.goosegame.view.playcard.api;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import java.util.List;

/**
 * API for the view that displays and manages the interaction with a player's card.
 */
public interface PlayerCardView {
    /**
     * Shows the card to the user, allowing possible actions.
     * @param card the card to display
     * @param otherPlayers the list of possible target players (if needed)
     */
    void showCard(Card card, List<Player> otherPlayers);

    /**
     * Closes the card view.
     */
    void close();

    /**
     * Updates the view (if needed) with the current card and possible actions.
     * @param card the card to update
     * @param otherPlayers the list of possible target players (if needed)
     */
    void update(Card card, List<Player> otherPlayers);
}
