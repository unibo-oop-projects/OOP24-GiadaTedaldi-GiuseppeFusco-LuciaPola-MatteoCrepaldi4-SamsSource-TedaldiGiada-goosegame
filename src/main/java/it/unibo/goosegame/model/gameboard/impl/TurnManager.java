package it.unibo.goosegame.model.gameboard.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.unibo.goosegame.utilities.player.api.Player;

/**
 * This class manages the turn order of players in game, including the ability to skip turns.
 */
public class TurnManager {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private final List<Player> players;
    private final Map<Player, Integer> skipTurns;
    private int currentIndex;

    /**
     * Constructs a TurnManager with the given list of players.
     * The list must contain between the MIN_PLAYERS and MAX_PLAYERS inclusive.
     * 
     * @param players the list of players participating in the game
     * @throws IllegalArgumentException if the player list is null, has fewer than 2 or more than 4 players
     */
    public TurnManager(final List<Player> players) {
        if (players == null) {
            throw new IllegalArgumentException("La lista dei giocatori non può essere nulla");
        }
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("Il numero dei giocatori deve essere compreso tra 2 e 4");
        }
        this.players = List.copyOf(players);
        this.currentIndex = 0;
        this.skipTurns = players.stream()
            .collect(Collectors.toMap(p -> p, p -> 0));
    }

    /**
     * @return the current player 
     */
    public Player getCurrentPlayer() {
        return this.players.get(this.currentIndex);
    }

    /**
     * Advances to the next player's turn.
     * If a player has any turns to skip, their skip count is decreased.
     * 
     * @return the next player who will take their turn 
     */
    public Player nextTurn() {
        while (true) {
            this.currentIndex = (this.currentIndex + 1) % this.players.size();
            final Player currentPlayer = this.getCurrentPlayer();
            final int skips = this.skipTurns.getOrDefault(currentPlayer, 0);
            if (skips > 0) {
                this.skipTurns.put(currentPlayer, skips - 1);
            } else {
                return currentPlayer;
            }
        }
    }

    /**
     * Increments the number of turns the specified player must skip by a given amount.
     *
     * @param player the player who will skip their next turn
     * @param turns the number of turns the player will skip
     * @throws IllegalArgumentException if the player is not part of the current player list or if turns is negative or zero
     */
    public void skipNextTurn(final Player player, final int turns) {
        if (!this.skipTurns.containsKey(player)) {
            throw new IllegalArgumentException("Giocatore " + player.getName() + " non in lista");
        }
        if (turns <= 0) {
            throw new IllegalArgumentException("Number of turns to skip must be positive");
        }
        this.skipTurns.compute(player, (p, count) -> count + turns);
    }
}
