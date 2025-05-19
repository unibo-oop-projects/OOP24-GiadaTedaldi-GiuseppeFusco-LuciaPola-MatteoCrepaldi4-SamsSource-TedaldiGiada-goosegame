package it.unibo.goosegame.model.playcard.api;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;

/**
 * API for the model that manages card logic for the play card phase.
 */
public interface PlayCardModel {
    /**
     * Draws a card based on the minigame state.
     * @param state the state of the minigame (WON/LOST)
     * @return the drawn card
     */
    Card drawCard(GameState state);

    /**
     * Checks if the card can be added to the player's satchel after a minigame.
     * @param card the card to check
     * @param currentPlayer the player
     * @param gameState the minigame state
     * @return true if the card can be added, false otherwise
     */
    boolean canAddToSatchel(Card card, Player currentPlayer, GameState gameState);

    /**
     * Checks if the card can be played from the satchel (without considering GameState).
     * @param card the card to check
     * @param currentPlayer the player
     * @return true if the card can be played, false otherwise
     */
    boolean canPlayCardFromSatchel(Card card, Player currentPlayer);

    /**
     * Checks if the card is a self-remove card.
     * @param card the card to check
     * @return true if it is a self-remove card, false otherwise
     */
    boolean isRemoveSelf(Card card);

    /**
     * Checks if the card is a remove-opponent card.
     * @param card the card to check
     * @return true if it is a remove-opponent card, false otherwise
     */
    boolean isRemoveOpponent(Card card);

    /**
     * Checks if the card is a throwable malus.
     * @param card the card to check
     * @return true if it is a throwable malus, false otherwise
     */
    boolean isMalusThrowable(Card card);

    /**
     * Checks if the card is a non-throwable malus.
     * @param card the card to check
     * @return true if it is a non-throwable malus, false otherwise
     */
    boolean isMalusNotThrowable(Card card);

    /**
     * Checks if the card is a bonus.
     * @param card the card to check
     * @return true if it is a bonus, false otherwise
     */
    boolean isBonus(Card card);
}
