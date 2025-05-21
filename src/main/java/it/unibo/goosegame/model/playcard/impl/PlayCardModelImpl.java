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
        // Nessuna inizializzazione necessaria per ora
    }

    /**
     * Estrae una carta random in base allo stato del minigioco.
     * Se WON: bonus, se LOST: malus.
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
     * Verifica se la carta può essere aggiunta al satchel del giocatore corrente (dopo minigioco).
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
     * Verifica se la carta può essere giocata dal satchel (senza considerare GameState).
     */
    @Override
    public boolean canPlayCardFromSatchel(final Card card, final Player currentPlayer) {
        if (card == null) {
            return false;
        }
        if (card.isRemove() && !card.isBonus()) {
            return false; // self remove non può essere rimessa
        }
        return !(!card.isBonus() && !card.isThrowable()); // malus non throwable non può essere lanciato
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
