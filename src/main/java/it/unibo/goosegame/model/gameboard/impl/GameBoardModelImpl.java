package it.unibo.goosegame.model.gameboard.impl;

import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.model.player.api.Player;
import it.unibo.goosegame.model.player.impl.PlayerImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of {@link GameBoardModel}.
 */
public class GameBoardModelImpl implements GameBoardModel {
    private final int numberOfPlayers;
    private final List<Player> players;

    /**
     * Constructor for the gameboard model class.
     *
     * @param numPlayers number of players for this session
     */
    public GameBoardModelImpl(final int numPlayers) {
        this.numberOfPlayers = numPlayers;
        players = new ArrayList<>();
        initPlayers();
    }

    /**
     * Initializes the player objects in a list.
     */
    private void initPlayers() {
        for (int i = 0; i < this.numberOfPlayers; i++) {
            final Player p = new PlayerImpl("Player " + (i + 1), i);
            players.add(p);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getWindowTitle() {
        return "Goose Game";
    }

    /**
     * Getter method for the number of players.
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Getter method for the player list.
     *
     * @return list of the players
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }
}
