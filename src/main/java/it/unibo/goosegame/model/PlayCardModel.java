package it.unibo.goosegame.model;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PlayCardModel {
    private final Random random = new Random();

    public PlayCardModel() {
        // Nessuna inizializzazione necessaria per ora
    }

    /**
     * Estrae una carta random in base allo stato del minigioco.
     * Se WON: bonus, se LOST: malus.
     */
    public Card drawCard(GameState state) {
        List<Card> pool = List.of(Card.values()).stream()
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
    public boolean canAddToSatchel(Card card, Player currentPlayer, GameState gameState) {
        if (card == null) return false;
        if (!card.isBonus() && gameState == GameState.LOST) return false;
        if (card.isRemove() && !card.isBonus()) return false;
        if (currentPlayer.getSatchel().isFull()) return false;
        if (!card.isBonus() && !card.isThrowable()) return false;
        return true;
    }

    /**
     * Verifica se la carta può essere giocata dal satchel (senza considerare GameState).
     */
    public boolean canPlayCardFromSatchel(Card card, Player currentPlayer) {
        if (card == null) return false;
        if (card.isRemove() && !card.isBonus()) return false; // self remove non può essere rimessa
        if (!card.isBonus() && !card.isThrowable()) return false; // malus non throwable non può essere lanciato
        // Altri controlli se servono
        return true;
    }

    /**
     * Metodi di sola logica/regola sulle carte.
     */
    public boolean isRemoveSelf(Card card) {
        return card != null && card.isRemove() && !card.isBonus();
    }

    public boolean isRemoveOpponent(Card card) {
        return card != null && card.isRemove() && card.isBonus();
    }

    public boolean isMalusThrowable(Card card) {
        return card != null && !card.isBonus() && card.isThrowable();
    }

    public boolean isMalusNotThrowable(Card card) {
        return card != null && !card.isBonus() && !card.isThrowable();
    }

    public boolean isBonus(Card card) {
        return card != null && card.isBonus();
    }
}
