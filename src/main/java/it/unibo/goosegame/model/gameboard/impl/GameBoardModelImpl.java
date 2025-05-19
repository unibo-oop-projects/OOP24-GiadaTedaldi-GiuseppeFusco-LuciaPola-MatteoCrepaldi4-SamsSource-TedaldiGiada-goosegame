package it.unibo.goosegame.model.gameboard.impl;

import it.unibo.goosegame.model.gameboard.api.GameBoardModel;
import it.unibo.goosegame.utilities.player.api.Player;
import it.unibo.goosegame.utilities.player.impl.PlayerImpl;

import java.util.ArrayList;
import java.util.List;

public class GameBoardModelImpl implements GameBoardModel {
    private final int numberOfPlayers;
    List<Player> players;

    public GameBoardModelImpl(int numPlayers) {
        players = new ArrayList<>();
        this.numberOfPlayers = numPlayers;
        initPlayers();
    }

    /**
     * Initializes the player objects in a list
     */
    private void initPlayers() {
        for (int i = 0; i < this.numberOfPlayers; i++) {
            Player p = new PlayerImpl("Player " + (i + 1), Player.COLORS[i]);
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
     * Getter method for the number of players
     *
     * @return the number of players
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
