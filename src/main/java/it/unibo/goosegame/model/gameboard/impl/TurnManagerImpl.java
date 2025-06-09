package it.unibo.goosegame.model.gameboard.impl;

import java.util.List;

import it.unibo.goosegame.model.gameboard.api.TurnManager;
import it.unibo.goosegame.model.player.api.Player;

/**
 * Implementation of the {@link TurnManager} interaface.
 */
public class TurnManagerImpl implements TurnManager {
    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 4;
    private final List<Player> players;
    private int currentIndex;

    /**
     * Constructs a TurnManager with the given list of players.
     * The list must contain between the MIN_PLAYERS and MAX_PLAYERS inclusive.
     * 
     * @param players the list of players participating in the game
     * @throws IllegalArgumentException if the player list is null, has fewer than 2 or more than 4 players
     */
    public TurnManagerImpl(final List<Player> players) {
        if (players == null) {
            throw new IllegalArgumentException("The list of players cannot be null!");
        }
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("The number of players must be between 2 and 4");
        }
        this.players = List.copyOf(players);
        this.currentIndex = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.currentIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player nextTurn() {
        this.currentIndex = (this.currentIndex + 1) % this.players.size();
        return this.getCurrentPlayer();
    }

}
