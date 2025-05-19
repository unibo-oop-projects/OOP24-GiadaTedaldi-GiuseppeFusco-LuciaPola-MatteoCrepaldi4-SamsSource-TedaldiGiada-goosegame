package it.unibo.goosegame.model.playcard.impl;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import it.unibo.goosegame.model.playcard.api.PlayCardModel;

public class PlayCardModelImpl implements PlayCardModel {
    private final Random random = new Random();

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
        if (card == null) {
            return false;
        }
        if (!card.isBonus() && gameState == GameState.LOST) {
            return false;
        }
        if (card.isRemove() && !card.isBonus()) {
            return false;
        }
        if (currentPlayer.getSatchel().isFull()) {
            return false;
        }
        return !( !card.isBonus() && !card.isThrowable() );
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
        return !( !card.isBonus() && !card.isThrowable() ); // malus non throwable non può essere lanciato
    }

    /**
     * Metodi di sola logica/regola sulle carte.
     */
    @Override
    public boolean isRemoveSelf(final Card card) {
        return card != null && card.isRemove() && !card.isBonus();
    }

    @Override
    public boolean isRemoveOpponent(final Card card) {
        return card != null && card.isRemove() && card.isBonus();
    }

    @Override
    public boolean isMalusThrowable(final Card card) {
        return card != null && !card.isBonus() && card.isThrowable();
    }

    @Override
    public boolean isMalusNotThrowable(final Card card) {
        return card != null && !card.isBonus() && !card.isThrowable();
    }

    @Override
    public boolean isBonus(final Card card) {
        return card != null && card.isBonus();
    }
}
