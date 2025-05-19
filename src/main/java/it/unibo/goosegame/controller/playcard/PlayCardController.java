package it.unibo.goosegame.controller.playcard;

import it.unibo.goosegame.model.playcard.impl.PlayCardModelImpl;
import it.unibo.goosegame.model.general.MinigamesModel.GameState;
import it.unibo.goosegame.utilities.Card;
import it.unibo.goosegame.utilities.Player;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing play card logic after minigames and from the satchel.
 */
public final class PlayCardController {
    private final List<Player> allPlayers;
    private final Player currentPlayer;
    private final GameState gameState; // può essere null se non serve
    private final PlayCardModelImpl model;
    private Card drawnCard;

    /**
     * Constructor for post-minigame phase (with GameState).
     * @param currentPlayer the player who just played
     * @param allPlayers the list of all players
     * @param gameState the state of the minigame
     */
    public PlayCardController(final Player currentPlayer, final List<Player> allPlayers, final GameState gameState) {
        this.currentPlayer = currentPlayer;
        this.allPlayers = allPlayers;
        this.gameState = gameState;
        this.model = new PlayCardModelImpl();
    }

    /**
     * Constructor for satchel management (without GameState).
     * @param currentPlayer the player who is playing
     * @param allPlayers the list of all players
     */
    public PlayCardController(final Player currentPlayer, final List<Player> allPlayers) {
        this.currentPlayer = currentPlayer;
        this.allPlayers = allPlayers;
        this.gameState = null;
        this.model = new PlayCardModelImpl();
    }

    /**
     * Draws a card based on the minigame state and stores it.
     */
    public void drawCard() {
        this.drawnCard = model.drawCard(gameState);
    }

    /**
     * Returns the drawn card.
     * @return the drawn card
     */
    public Card getDrawnCard() {
        return drawnCard;
    }

    /**
     * Checks if the drawn card can be added to the satchel (after minigame).
     * @return true if the card can be added, false otherwise
     */
    public boolean canAddToSatchel() {
        return model.canAddToSatchel(drawnCard, currentPlayer, gameState);
    }

    /**
     * Checks if a card can be played from the satchel (without GameState).
     * @param card the card to check
     * @return true if the card can be played, false otherwise
     */
    public boolean canPlayCardFromSatchel(final Card card) {
        return model.canPlayCardFromSatchel(card, currentPlayer);
    }

    /**
     * Adds the drawn card to the current player's satchel if allowed.
     * @return true if added, false otherwise
     */
    public boolean addCardToSatchel() {
        if (canAddToSatchel()) {
            return currentPlayer.getSatchel().addCard(drawnCard);
        }
        return false;
    }

    /**
     * Applies the drawn card to the current player (self).
     * Here goes the logic for applying effects.
     */
    public void playCardOnSelf() {
        if (model.isRemoveSelf(drawnCard)) {
            currentPlayer.getSatchel().clear();
        }
        if (model.isMalusNotThrowable(drawnCard)) {
            // no-op (TODO: implement logic to move player on board)
        }
        if (model.isBonus(drawnCard)) {
            // no-op (TODO: implement logic to apply bonus)
        }
    }

    /**
     * Checks if the card is a self-remove card.
     * @param card the card to check
     * @return true if the card is a self-remove card, false otherwise
     */
    public boolean isRemoveSelf(final Card card) {
        return model.isRemoveSelf(card);
    }

    /**
     * Checks if the card is a remove-opponent card.
     * @param card the card to check
     * @return true if the card is a remove-opponent card, false otherwise
     */
    public boolean isRemoveOpponent(final Card card) {
        return model.isRemoveOpponent(card);
    }

    /**
     * Checks if the card is a throwable malus.
     * @param card the card to check
     * @return true if the card is a throwable malus, false otherwise
     */
    public boolean isMalusThrowable(final Card card) {
        return model.isMalusThrowable(card);
    }

    /**
     * Checks if the card is a non-throwable malus.
     * @param card the card to check
     * @return true if the card is a non-throwable malus, false otherwise
     */
    public boolean isMalusNotThrowable(final Card card) {
        return model.isMalusNotThrowable(card);
    }

    /**
     * Checks if the card is a bonus.
     * @param card the card to check
     * @return true if the card is a bonus, false otherwise
     */
    public boolean isBonus(final Card card) {
        return model.isBonus(card);
    }

    /**
     * Applies the drawn card to another player (e.g., to throw or remove).
     * @param target the target player
     */
    public void playCardOnPlayer(final Player target) {
        if (model.isRemoveOpponent(drawnCard)) {
            target.getSatchel().clear();
        } else if (model.isMalusThrowable(drawnCard)) {
            // no-op (TODO: implement logic to move player on board)
        }
        // Other special effects if needed
    }

    /**
     * Returns the current player (the one who just finished the minigame).
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the list of other players (possible targets).
     * @return the list of other players
     */
    public List<Player> getOtherPlayers() {
        return allPlayers.stream()
            .filter(p -> !p.equals(currentPlayer))
            .collect(Collectors.toList());
    }
}
