package it.unibo.goosegame.controller.playcard;

import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.model.playcard.impl.PlayCardModelImpl;

import java.util.List;
import java.util.stream.Collectors;

public class PlayCardController {
    private final List<Player> allPlayers;
    private final Player currentPlayer;
    private final GameState gameState; // può essere null se non serve
    private final PlayCardModelImpl model;
    private Card drawnCard;

    // Costruttore per la fase post-minigioco (con GameState)
    public PlayCardController(Player currentPlayer, List<Player> allPlayers, GameState gameState) {
        this.currentPlayer = currentPlayer;
        this.allPlayers = allPlayers;
        this.gameState = gameState;
        this.model = new PlayCardModelImpl();
    }

    // Costruttore per la gestione dal satchel (senza GameState)
    public PlayCardController(Player currentPlayer, List<Player> allPlayers) {
        this.currentPlayer = currentPlayer;
        this.allPlayers = allPlayers;
        this.gameState = null;
        this.model = new PlayCardModelImpl();
    }

    /**
     * Estrae una carta in base allo stato del minigioco e la memorizza.
     */
    public void drawCard() {
        this.drawnCard = model.drawCard(gameState);
    }

    /**
     * Restituisce la carta estratta.
     */
    public Card getDrawnCard() {
        return drawnCard;
    }

    /**
     * Verifica se la carta estratta può essere aggiunta al satchel (dopo minigioco).
     */
    public boolean canAddToSatchel() {
        return model.canAddToSatchel(drawnCard, currentPlayer, gameState);
    }

    /**
     * Verifica se una carta può essere giocata dal satchel (senza GameState).
     */
    public boolean canPlayCardFromSatchel(Card card) {
        return model.canPlayCardFromSatchel(card, currentPlayer);
    }

    /**
     * Aggiunge la carta estratta al satchel del giocatore corrente.
     * (Effettua l'aggiunta vera e propria, se permesso)
     * @return true se aggiunta, false altrimenti
     */
    public boolean addCardToSatchel() {
        if (canAddToSatchel()) {
            return currentPlayer.getSatchel().addCard(drawnCard);
        }
        return false;
    }

    /**
     * Applica la carta estratta al giocatore corrente (se stesso).
     * Qui va la logica di applicazione degli effetti.
     */
    public void playCardOnSelf() {
        if (model.isRemoveSelf(drawnCard)) {
            currentPlayer.getSatchel().clear();
        }
        // Se la carta è malus non throwable, applica malus a se stesso
        if (model.isMalusNotThrowable(drawnCard)) {
            // Esempio: muovi indietro di drawnCard.getSteps()
            // TODO: chiama qui la logica per muovere il player sul tabellone
        }
        // Se la carta è bonus, applica bonus a se stesso
        if (model.isBonus(drawnCard)) {
            // TODO: logica per applicare bonus
        }
    }

    /**
     * Wrapper per la view: controlla se la carta è self remove.
     */
    public boolean isRemoveSelf(Card card) {
        return model.isRemoveSelf(card);
    }

    public boolean isRemoveOpponent(Card card) {
        return model.isRemoveOpponent(card);
    }

    public boolean isMalusThrowable(Card card) {
        return model.isMalusThrowable(card);
    }

    public boolean isMalusNotThrowable(Card card) {
        return model.isMalusNotThrowable(card);
    }

    public boolean isBonus(Card card) {
        return model.isBonus(card);
    }

    /**
     * Applica la carta estratta a un altro giocatore (ad esempio per lanciare o rimuovere).
     * @param target il giocatore bersaglio
     */
    public void playCardOnPlayer(Player target) {
        if (model.isRemoveOpponent(drawnCard)) {
            target.getSatchel().clear();
        } else if (model.isMalusThrowable(drawnCard)) {
            // Applica malus: ad esempio, muovi indietro di drawnCard.getSteps()
            // TODO: chiama qui la logica per muovere il player sul tabellone
        }
        // Altri effetti speciali se servono
    }

    /**
     * Restituisce il giocatore corrente (colui che ha appena finito il minigioco).
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Restituisce la lista degli altri giocatori (possibili bersagli).
     */
    public List<Player> getOtherPlayers() {
        return allPlayers.stream()
            .filter(p -> !p.equals(currentPlayer))
            .collect(Collectors.toList());
    }
}
