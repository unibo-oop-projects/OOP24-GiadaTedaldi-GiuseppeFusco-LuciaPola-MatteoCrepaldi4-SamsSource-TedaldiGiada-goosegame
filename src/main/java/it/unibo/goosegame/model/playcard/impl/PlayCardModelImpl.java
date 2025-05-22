package it.unibo.goosegame.model.playcard.impl;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.goosegame.model.playcard.api.PlayCardModel;

/**
 * Implementation of the PlayCardModel interface.
 * This class manages the logic for drawing and validating cards after minigames and from the satchel.
 */
public final class PlayCardModelImpl implements PlayCardModel {
    private final Random random = new Random();

    /**
     * Constructs a new PlayCardModelImpl.
     */
    public PlayCardModelImpl() {
        // No initialization needed for now
    }

    /**
     * Draws a random card based on the minigame state.
     * If WON: bonus, if LOST: malus.
     * @param state the result of the minigame
     * @return the drawn card
     */
    @Override
    public Card drawCard(final GameState state) {
        final List<Card> pool = List.of(Card.values()).stream()
            .filter(c -> state == GameState.WON ? c.isBonus() : !c.isBonus())
            .collect(Collectors.toList());
        if (pool.isEmpty()) {
            return null;
        } else {
            return pool.get(random.nextInt(pool.size()));
        }
    }

    /**
     * Checks if the card can be added to the current player's satchel (after minigame).
     * @param card the card to check
     * @param currentPlayer the player
     * @param gameState the minigame state
     * @return true if the card can be added, false otherwise
     */
    @Override
    public boolean canAddToSatchel(final Card card, final Player currentPlayer, final GameState gameState) {
        return card != null
        && (card.isBonus() || gameState != GameState.LOST)
        && (!card.isRemove() || card.isBonus())
        && !currentPlayer.getSatchel().isFull()
        && (card.isBonus() || card.isThrowable());
    }

    /**
     * Checks if the card can be played from the satchel (without considering GameState).
     * @param card the card to check
     * @param currentPlayer the player
     * @return true if the card can be played, false otherwise
     */
    @Override
    public boolean canPlayCardFromSatchel(final Card card, final Player currentPlayer) {
        if (card == null) {
            return false;
        }
        if (card.isRemove() && !card.isBonus()) {
            return false; // self remove cannot be put back
        }
        return !(!card.isBonus() && !card.isThrowable()); // non-throwable malus cannot be thrown
    }

    /**
     * Checks if the card is a self-remove card.
     * @param card the card to check
     * @return true if the card is a self-remove card, false otherwise
     */
    @Override
    public boolean isRemoveSelf(final Card card) {
        return card != null && card.isRemove() && !card.isBonus();
    }

    /**
     * Checks if the card is a remove-opponent card.
     * @param card the card to check
     * @return true if the card is a remove-opponent card, false otherwise
     */
    @Override
    public boolean isRemoveOpponent(final Card card) {
        return card != null && card.isRemove() && card.isBonus();
    }

    /**
     * Checks if the card is a throwable malus.
     * @param card the card to check
     * @return true if the card is a throwable malus, false otherwise
     */
    @Override
    public boolean isMalusThrowable(final Card card) {
        return card != null && !card.isBonus() && card.isThrowable();
    }

    /**
     * Checks if the card is a non-throwable malus.
     * @param card the card to check
     * @return true if the card is a non-throwable malus, false otherwise
     */
    @Override
    public boolean isMalusNotThrowable(final Card card) {
        return card != null && !card.isBonus() && !card.isThrowable();
    }

    /**
     * Checks if the card is a bonus.
     * @param card the card to check
     * @return true if the card is a bonus, false otherwise
     */
    @Override
    public boolean isBonus(final Card card) {
        return card != null && card.isBonus();
    }
}
